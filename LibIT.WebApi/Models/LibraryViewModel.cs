using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LibIT.WebApi.Models
{
    public class FilterViewModel
    {
        public int CurrentPage { get; set; }
    }

    public class CategoryViewModel
    {
        public long Id { get; set; }
        public string Name { get; set; }
        public string Image { get; set; }
    }

    public class BookViewModel
    {
        public long Id { get; set; }
        public string Author { get; set; }
        public string Name { get; set; }
        public string Image { get; set; }
        public long CategoryId { get; set; }
    }

    public class BookAddViewModel
    {
        public string Author { get; set; }
        public string Name { get; set; }
        public string Image { get; set; }
        public long CategoryId { get; set; }
    }
}
