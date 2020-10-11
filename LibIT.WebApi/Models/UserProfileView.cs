﻿using LibIT.WebApi.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LibIT.WebApi.Models
{
    public class UserProfileView
    {
        public string Name { get; set; }
        public string Surname { get; set; }
        public string Photo { get; set; }
        public DateTime? DateOfBirth { get; set; }
        public string Phone { get; set; }
        public DateTime RegistrationDate { get; set; }

        public UserProfileView(UserProfile profile)
        {
            Name = profile.Name;
            Surname = profile.Surname;
            Photo = profile.Photo;
            DateOfBirth = profile.DateOfBirth;
            Phone = profile.Phone;
            RegistrationDate = profile.RegistrationDate;
        }
    }
}