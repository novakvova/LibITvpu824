using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LibIT.WebApi.Models
{
    public class UserLoginViewModel
    {
        public string Email { get; set; }
        public string Password { get; set; }
    }

    public class UserRegisterViewModel
    {
        public string Email { get; set; }
        public string ImageBase64 { get; set; }
        public string Password { get; set; }
    }
}
