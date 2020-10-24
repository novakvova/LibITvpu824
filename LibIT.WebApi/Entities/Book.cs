using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace LibIT.WebApi.Entities
{
    [Table("tblBooks")]
    public class Book
    {
        [Key]
        public long Id { get; set; }
        [Required, StringLength(255)]
        public string Author { get; set; }

        [Required, StringLength(255)]
        public string Name { get; set; }

        [StringLength(2555555)]
        public string Image { get; set; }

        [ForeignKey("Category")]
        public long CategoryId { get; set; }
        public Category Category { get; set; }
    }
}
