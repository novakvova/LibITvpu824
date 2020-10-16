using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using LibIT.WebApi.Entities;
using LibIT.WebApi.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace LibIT.WebApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    //[Authorize]
    public class LibraryController : ControllerBase
    {
        private readonly EFContext _context;
        public LibraryController(EFContext context)
        {
            _context = context;
        }

        [HttpGet("categories")]
        public IActionResult GetAllCategories()
        {
            var query = _context.Categories.AsQueryable();

            ICollection<CategoryViewModel> result;

            result = query.Select(c => new CategoryViewModel
            {
                Id = c.Id,
                Name = c.Name,
                Image = c.Image
            }).ToList();

            return Ok(result);
        }
    }
}