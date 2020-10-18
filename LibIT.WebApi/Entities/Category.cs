using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace LibIT.WebApi.Entities
{
    [Table("tblCategories")]
    public class Category
    {

        [Key]
        public long Id { get; set; }

        /// <summary>
        /// Name of category
        /// </summary>
        [Required, StringLength(255)]
        public string Name { get; set; }

        /// <summary>
        /// Picture of category 
        /// </summary>
        [StringLength(255)]
        public string Image { get; set; }
    }
}
