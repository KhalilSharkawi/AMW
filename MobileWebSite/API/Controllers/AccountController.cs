using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using API.Models;
//using Microsoft.Web.WebPages.OAuth;
using WebMatrix.WebData;
using API.Filters;

namespace API.Controllers
{
    [RoutePrefix("api/account")]
    public class AccountController : ApiController
    {
        private Model1 db = new Model1();

        public Response<bool> Login(string username , string password)
        {
            if (WebSecurity.Login(username, password))
            {
                return new Response<bool>("success", true, "", 1);
            }

            return new Response<bool>("fail", false, "Username or Password is wrong", -1);
        }

        [Route("all")]
        [HttpPost]
        public Response<List<UserProfile>> GetUserProfiles()
        {
            return new Response<List<UserProfile>>("success", db.UserProfiles.ToList(), "", 1);
        }

        // GET: api/UserProfiles/5
        [ResponseType(typeof(UserProfile))]
        [Route("one")]
        [HttpPost]
        public Response<UserProfile> GetUserProfile(int id)
        {
            UserProfile UserProfile = db.UserProfiles.Find(id);
            if (UserProfile == null)
            {
                return new Response<UserProfile>("Not Found", null, "Not Found", -1);
            }

            return new Response<UserProfile>("success", UserProfile, "", 1);
        }

        // PUT: api/UserProfiles/5
        [ResponseType(typeof(void))]
        [HttpPost]
        [Route("edit")]
        public Response<UserProfile> EditUserProfile([FromBody]UserProfile UserProfile)
        {
            if (!ModelState.IsValid)
            {
                return new Response<UserProfile>("Bad Request", null, "Bad Request", -2);
            }

            db.Entry(UserProfile).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserProfileExists(UserProfile.UserId))
                {
                    return new Response<UserProfile>("Not Found", null, "Not Found", -1);
                }
                else
                {
                    throw;
                }
            }

            return new Response<UserProfile>("success", UserProfile, "", 1);
        }

        // POST: api/UserProfiles
        [ResponseType(typeof(UserProfile))]
        [Route("create")]
        [HttpPost]
        public Response<UserProfile> createUserProfile([FromBody]UserProfile UserProfile)
        {
            if (!ModelState.IsValid)
            {
                return new Response<UserProfile>("Bad Request", null, "Bad Request", -2);
            }

            db.UserProfiles.Add(UserProfile);
            db.SaveChanges();

            return new Response<UserProfile>("success", UserProfile, "", 1);
        }

        // DELETE: api/UserProfiles/5
        [ResponseType(typeof(void))]
        [HttpPost]
        [Route("delete")]
        public Response<UserProfile> deleteUserProfile([FromBody]UserProfile reg)
        {
            UserProfile UserProfile = db.UserProfiles.Find(reg.UserId);
            if (UserProfile == null)
            {
                return new Response<UserProfile>("Not Found", null, "Not Found", -1);
            }

            db.UserProfiles.Remove(UserProfile);
            db.SaveChanges();

            return new Response<UserProfile>("success", null, "", 1);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool UserProfileExists(int id)
        {
            return db.UserProfiles.Count(e => e.UserId == id) > 0;
        }
    }
}