using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using LibIT.WebApi.Entities;
using LibIT.WebApi.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace LibIT.WebApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class ProfileController : ControllerBase
    {
        private readonly EFContext _context;
        public ProfileController(EFContext context)
        {
            _context = context;
        }

        [HttpPost("info")]
        public IActionResult GetInfo()
        {
            string userName;
            try
            {
                userName = User.Claims.FirstOrDefault(x => x.Type == "name").Value;
            }
            catch (Exception)
            {
                return BadRequest("Потрібно спочатку залогінитися!");
            }

            if (string.IsNullOrEmpty(userName))
            {
                return BadRequest("Потрібно спочатку залогінитися!");

            }

            var query = _context.Users.Include(x => x.UserProfile).AsQueryable();
            var user = query.FirstOrDefault(c => c.UserName == userName);

            if (user == null)
            {
                return BadRequest("Поганий запит!");
            }

            UserProfileView userProfile = new UserProfileView(user.UserProfile); 
            return Ok(userProfile);
        }

        [HttpPost("editprofile")]
        public IActionResult EditProfile([FromBody] UserProfileView model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest("Передані некоректні дані");
            }

            string userName;
            try
            {
                userName = User.Claims.FirstOrDefault(x => x.Type == "name").Value;
            }
            catch (Exception)
            {
                return BadRequest("Потрібно спочатку залогінитися!");
            }

            var query = _context.Users.Include(x => x.UserProfile).AsQueryable();
            var user = query.FirstOrDefault(c => c.UserName == userName);

            if (user == null)
            {
                return BadRequest("");
            }

            UserProfileView userProfile = new UserProfileView(user.UserProfile);

            user.UserProfile.Name = model.Name;
            user.UserProfile.Surname = model.Surname;
            user.UserProfile.DateOfBirth = model.DateOfBirth;
            user.UserProfile.Phone = model.Phone;
            user.UserProfile.Photo = model.Photo;
            user.PhoneNumber = model.Phone;
            _context.SaveChanges();
                        
            return Ok(userProfile);
        }
    }
}

