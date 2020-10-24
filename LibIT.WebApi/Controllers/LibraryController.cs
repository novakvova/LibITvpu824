using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using LibIT.WebApi.Entities;
using LibIT.WebApi.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Hosting;
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
        private readonly IWebHostEnvironment _env;
        public LibraryController(EFContext context, IWebHostEnvironment env)
        {
            _context = context;
            _env = env;
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

        [HttpPost("addbook")]
        public IActionResult Create([FromBody]BookAddViewModel model)
        {
            var book = _context.Books
                .SingleOrDefault(c => c.Name == model.Name);
            if (book != null)
            {
                return BadRequest(new { invalid = "Such a book is in the database" });
            }
            long length = model.Image.Length;

            //string ext = ".fb2";
            //string fileName = Path.GetRandomFileName() + ext;
            //Byte[] bytes1 = File.ReadAllBytes("path");
            //var serverPath = _env.ContentRootPath;
            //var folderName = "Uploaded";
            //var path = Path.Combine(serverPath, folderName);


            //Byte[] bytes = Convert.FromBase64String(model.Image);
            //File.WriteAllBytes(path, bytes);

            //FileStream fs = new FileStream(txtOutFile.Text,
            //                      FileMode.CreateNew,
            //                      FileAccess.Write,
            //                      FileShare.None);
            //fs.Write(filebytes, 0, filebytes.Length);
            //fs.Close();


            //File.WriteAllBytes(@"c:\yourfile", Convert.FromBase64String(model.Image));
            //   var bmp = model.ImageBase64.FromBase64StringToImage();
            //var serverPath = _env.ContentRootPath;
            //var folderName = "Uploaded";
            //var path = Path.Combine(serverPath, folderName);

            //if (!Directory.Exists(path))
            //{
            //    Directory.CreateDirectory(path);
            //}

            //try
            //{
            //    string filePathSave = Path.Combine(path, fileName);
            //    bmp.Save(filePathSave, ImageFormat.Jpeg);

            //    // Check if file exists with its full path    
            //    if (!String.IsNullOrWhiteSpace(user.UserProfile.Photo) && System.IO.File.Exists(Path.Combine(path, user.UserProfile.Photo)))
            //    {
            //        // If file found, delete it    
            //        System.IO.File.Delete(Path.Combine(path, user.UserProfile.Photo));
            //        Console.WriteLine("File deleted.");
            //    }
            //    else Console.WriteLine("File not found");
            //}
            //catch (IOException ioExp)
            //{
            //    return BadRequest(ioExp.Message);
            //}

            book = new Book
            {
                Author = model.Author,
                Name = model.Name,
                Image = model.Image,
                //Image = "prata_c.jpg",
                CategoryId = model.CategoryId
            };
            _context.Books.Add(book);
            _context.SaveChanges();
            return Ok(new BookViewModel
            {
                Id = book.Id,
                Author = book.Author,
                Name = book.Name,
                Image = book.Image,
                CategoryId = book.CategoryId

            });
        }
    }
}