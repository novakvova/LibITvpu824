using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using LibIT.WebApi.Entities;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;

namespace LibIT.WebApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
  
    public class ProfileController : ControllerBase
    {
        private readonly UserManager<DbUser> _userManager;

        public ProfileController(UserManager<DbUser> userManager)
        {
            _userManager = userManager;
        }




        // [HttpPost("info")]
        [HttpGet("info")]
       // [Authorize("Bearer")]
        public async Task<IActionResult> Info()
        {

            var userName = User.Claims.FirstOrDefault(x => x.Type == "name").Value;
            var user = await _userManager.FindByNameAsync(userName);
            string test = "Home page for " + user.Email;
            return Ok(user);


            //var userName = User.Identity.Name;
            //if (User.Identity.IsAuthenticated)
            //{
            //    string test = "Home page for " + User.Identity.Name;
            //}
            //return Ok();

        }
    }
}
