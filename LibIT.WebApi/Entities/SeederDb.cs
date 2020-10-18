using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using System.Linq;

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

        public static void SeedCategories(EFContext _context) {
            if (_context.Categories.Count() <= 0)
            {
                var categories = new List<Category>();
                categories.Add(new Category
                {
                    Name = "C, C++",
                    Image = "cpp_logo.png"
                });

                categories.Add(new Category
                {
                    Name = ".NET, C#",
                    Image = "c_sharp_logo.png"
                }); 
                
                categories.Add(new Category
                {
                    Name = "Android",
                    Image = "android_logo.png"
                });

                categories.Add(new Category
                {
                    Name = "Java",
                    Image = "java_logo.png"
                });

                categories.Add(new Category
                {
                    Name = "Python",
                    Image = "phyton_logo.png"
                }); 
                
                categories.Add(new Category
                {
                    Name = "Database",
                    Image = "database_logo.png"
                });

                foreach (var category in categories)
                {
                    _context.Categories.Add(category);
                }

                _context.SaveChanges();
            }
        }

        public static void SeedDataByAS(IServiceProvider services)
        {
            using (var scope = services.GetRequiredService<IServiceScopeFactory>().CreateScope())
            {
                var manager = scope.ServiceProvider.GetRequiredService<UserManager<DbUser>>();
                var managerRole = scope.ServiceProvider.GetRequiredService<RoleManager<DbRole>>();
                var context = scope.ServiceProvider.GetRequiredService<EFContext>(); 
                SeederDB.SeedData(manager, managerRole);
                SeederDB.SeedCategories(context);
            }
        }
    }
}
