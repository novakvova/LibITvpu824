﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using LibIT.WebApi.Entities;
using LibIT.WebApi.Models;
using LibIT.WebApi.Services;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;

namespace LibIT.WebApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        private readonly EFContext _context;
        private readonly UserManager<DbUser> _userManager;
        private readonly SignInManager<DbUser> _signInManager;
        private readonly IJwtTokenService _IJwtTokenService;

        public AccountController(EFContext context,
           UserManager<DbUser> userManager,
           SignInManager<DbUser> signInManager,
           IJwtTokenService IJwtTokenService)
        {
            _userManager = userManager;
            _context = context;
            _signInManager = signInManager;
            _IJwtTokenService = IJwtTokenService;
        }
        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody]UserLoginViewModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest("Bad Model");
            }

            var user = _context.Users.FirstOrDefault(u => u.Email == model.Email);
            if (user == null)
            {
                return BadRequest("Даний користувач не знайденний!");
            }

            var result = _signInManager
                .PasswordSignInAsync(user, model.Password, false, false).Result;

            if (!result.Succeeded)
            {
                return BadRequest("Невірно введений пароль!");
            }

            await _signInManager.SignInAsync(user, isPersistent: false);

            return Ok(
                 new
                 {
                     token = _IJwtTokenService.CreateToken(user)
                 });
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody]UserViewModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest("Поганий запит");
            }
            var roleName = "User";
            var userReg = _context.Users.FirstOrDefault(x => x.Email == model.Email);
            if (userReg != null)
            {
                return BadRequest("Цей емейл вже зареєстровано!");
            }

            if (model.Email == null)
            {
                return BadRequest("Вкажіть пошту!");
            }
            else
            {
                var testmail = new Regex(@"^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$");
                if (!testmail.IsMatch(model.Email))
                {
                    return BadRequest("Невірно вказана почта!");
                }
            }
            DbUser user = new DbUser
            {
                Email = model.Email,
                UserName = model.Email,
                //Age = model.Age,
                //Phone = model.Phone,
                //Description = model.Description,
            };

            var res = _userManager.CreateAsync(user, model.Password).Result;
            if (!res.Succeeded)
            {
                return BadRequest("Код доступу має складатись з 8 символів, містити мінімум одну велику літеру!");
            }

            res = _userManager.AddToRoleAsync(user, roleName).Result;

            if (!res.Succeeded)
            {
                return BadRequest("Поганий запит!");
            }

            await _signInManager.SignInAsync(user, isPersistent: false);

            return Ok(
                 new
                 {
                     token = _IJwtTokenService.CreateToken(user)
                 });
        }
    }
}