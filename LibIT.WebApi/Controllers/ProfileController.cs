using System;
using System.Collections.Generic;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using LibIT.WebApi.Entities;
using LibIT.WebApi.Helpers;
using LibIT.WebApi.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Hosting;
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
        private readonly IWebHostEnvironment _env;
        public ProfileController(EFContext context, IWebHostEnvironment env)
        {
            _context = context;
            _env = env;
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
        //public IActionResult EditProfile([FromBody] UserProfileEditViewModel model)
        public IActionResult EditProfile([FromBody]UserProfileEditViewModel model)
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
            string ext = ".jpg";
            string fileName = Path.GetRandomFileName() + ext;

            var bmp = model.Photo.FromBase64StringToImage();
            var serverPath = _env.ContentRootPath; //Directory.GetCurrentDirectory(); //_env.WebRootPath;
            var folderName = "Uploaded";
            var path = Path.Combine(serverPath, folderName); //
            if (!Directory.Exists(path))
            {
                Directory.CreateDirectory(path);
            }


            string filePathSave = Path.Combine(path, fileName);

            bmp.Save(filePathSave, ImageFormat.Jpeg);




            // UserProfileEditViewModel userProfile = new UserProfileEditViewModel();

            user.UserProfile.Name = model.Name;
            user.UserProfile.Surname = model.Surname;
            user.UserProfile.DateOfBirth = model.DateOfBirth;
            user.UserProfile.Phone = model.Phone;
            user.UserProfile.Photo = fileName;
            //  user.PhoneNumber = model.Phone;

            //user.UserProfile.Name = "Pavlo";
            //user.UserProfile.Surname = "Pavloch";
            //// user.UserProfile.DateOfBirth = model.DateOfBirth;
            //user.UserProfile.Phone ="0791234569";
            //// user.UserProfile.Photo = model.Photo;
            ////  user.PhoneNumber = model.Phone;
            _context.SaveChanges();

            UserProfileEditViewModel userProfile = new UserProfileEditViewModel(user.UserProfile);
            return Ok(userProfile);
        }

        [HttpPost("allusers")]
        public IActionResult GetAllUsers1()
        {
            //var query = _context.TypesOfDishes.AsQueryable();
            var query = _context.Users.AsQueryable();

            GetAllUsersViewModel result = new GetAllUsersViewModel();

            result.AllUsers = query.Select(t => new GetUserViewModel
            {
                Email = t.Email,
                Name = t.UserProfile.Name
            }).ToList();

            return Ok(result.AllUsers);
        }

        [HttpGet("allusers")]
        public async Task<IActionResult> GetAllUsers()
        {
            var users = await _context.Users.Select(x => new GetUserViewModel
            {
                Email = x.Email,
                Name = x.UserProfile.Name
            }).ToListAsync();
            return Ok(users);
        }
    }
}

