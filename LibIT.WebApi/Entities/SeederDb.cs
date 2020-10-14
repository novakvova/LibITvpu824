﻿using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.DependencyInjection;
using System;

namespace LibIT.WebApi.Entities
{
    public class SeederDB
    {
        public static void SeedData(UserManager<DbUser> userManager, RoleManager<DbRole> roleManager)
        {
            var adminRoleName = "Admin";
            var userRoleName = "User";

            var roleResult = roleManager.FindByNameAsync(adminRoleName).Result;
            if (roleResult == null)
            {
                var roleresult = roleManager.CreateAsync(new DbRole
                {
                    Name = adminRoleName

                }).Result;
            }

            roleResult = roleManager.FindByNameAsync(userRoleName).Result;
            if (roleResult == null)
            {
                var roleresult = roleManager.CreateAsync(new DbRole
                {
                    Name = userRoleName

                }).Result;
            }

            var email = "admin@gmail.com";
            var findUser = userManager.FindByEmailAsync(email).Result;
            if (findUser == null)
            {
                var user = new DbUser
                {
                    Email = email,
                    UserName = email,
                };

                user.UserProfile = new UserProfile()
                {
                    Name = "Petro",
                    Surname = "Petunchik",
                    DateOfBirth = new DateTime(1980, 5, 20),
                    Phone = "+380978515659",
                    RegistrationDate = DateTime.Now,
                    Photo = "person_1.jpg"
                };

                var result = userManager.CreateAsync(user, "Qwerty1-").Result;
                result = userManager.AddToRoleAsync(user, adminRoleName).Result;
            }

            email = "user@gmail.com";
            findUser = userManager.FindByEmailAsync(email).Result;
            if (findUser == null)
            {
                var user = new DbUser
                {
                    Email = email,
                    UserName = email,
                };

                user.UserProfile = new UserProfile()
                {
                    Name = "Natalya",
                    Surname = "Pupenko",
                    DateOfBirth = new DateTime(1982, 10, 7),
                    Phone = "+380670015009",
                    RegistrationDate = DateTime.Now,
                    Photo = "person_2.jpg"
                };

                var result = userManager.CreateAsync(user, "Qwerty1-").Result;
                result = userManager.AddToRoleAsync(user, userRoleName).Result;
            }
        }

        public static void SeedDataByAS(IServiceProvider services)
        {
            using (var scope = services.GetRequiredService<IServiceScopeFactory>().CreateScope())
            {
                var manager = scope.ServiceProvider.GetRequiredService<UserManager<DbUser>>();
                var managerRole = scope.ServiceProvider.GetRequiredService<RoleManager<DbRole>>();
                SeederDB.SeedData(manager, managerRole);
            }
        }
    }
}
