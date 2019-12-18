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
using WebSite.Models;

namespace WebSite.API
{
    //[RoutePrefix("api/customers")]
    public class CustomersController : ApiController
    {
        private Model1 db = new Model1();

        //[Route("all")]
        [HttpPost]
        public Response<List<Customer>> all()
        {
            return new Response<List<Customer>>("success", db.Customers.ToList(), "", 1);
        }

        // GET: api/Customers/5
        //[ResponseType(typeof(Customer))]
        //[Route("one")]
        [HttpPost]
        public Response<Customer> one(int id)
        {
            Customer Customer = db.Customers.Find(id);
            if (Customer == null)
            {
                return new Response<Customer>("Not Found", null, "Not Found", -1);
            }

            return new Response<Customer>("success", Customer, "", 1);
        }

        // PUT: api/Customers/5
        //[ResponseType(typeof(void))]
        [HttpPost]
        //[Route("edit")]
        public Response<Customer> edit([FromBody]Customer Customer)
        {
            if (!ModelState.IsValid)
            {
                return new Response<Customer>("Bad Request", null, "Bad Request", -2);
            }

            db.Entry(Customer).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CustomerExists(Customer.Id))
                {
                    return new Response<Customer>("Not Found", null, "Not Found", -1);
                }
                else
                {
                    throw;
                }
            }

            return new Response<Customer>("success", Customer, "", 1);
        }

        // POST: api/Customers
        //[ResponseType(typeof(Customer))]
        //[Route("create")]
        [HttpPost]
        public Response<Customer> create([FromBody]Customer Customer)
        {
            if (!ModelState.IsValid)
            {
                return new Response<Customer>("Bad Request", null, "Bad Request", -2);
            }

            db.Customers.Add(Customer);
            db.SaveChanges();

            return new Response<Customer>("success", Customer, "", 1);
        }

        // DELETE: api/Customers/5
        //[ResponseType(typeof(void))]
        [HttpPost]
        //[Route("delete")]
        public Response<Customer> delete([FromBody]Customer reg)
        {
            Customer Customer = db.Customers.Find(reg.Id);
            if (Customer == null)
            {
                return new Response<Customer>("Not Found", null, "Not Found", -1);
            }

            db.Customers.Remove(Customer);
            db.SaveChanges();

            return new Response<Customer>("success", null, "", 1);
        }
        [HttpGet]
        public Response<CustomerInfo> GetInfo(int id = 0)
        {
            var c = db.Customers.Find(id);
            var _5day = DateTime.Today.AddDays(-5);
            var _5daysAllownce = 40 - db.Transactions.Where(x => x.CustomerId == c.Id && x.Date >= _5day).Select(x => x.PetrolAmount).DefaultIfEmpty(0).Sum();
            var _monthlyAllownce = 100 - db.Transactions.Where(x => x.CustomerId == c.Id && x.Date.Month == DateTime.Now.Month).Select(x => x.PetrolAmount).DefaultIfEmpty(0).Sum();
            var free = db.Transactions.Where(x => x.CustomerId == c.Id).Sum(x => x.FreeAmount);
            var r = new CustomerInfo()
            {
                _5daysAllownce = _5daysAllownce,
                _free = free,
                _monthlyAllownce = _monthlyAllownce
            };
            return new Response<CustomerInfo>("success", r, "", 1);
        }
        [HttpGet]
        public Response<List<CustomerReport>> Report()
        {
            var result = db.Customers.ToList();
            var rrr = new List<CustomerReport>();
            foreach (var item in result)
            {
                var tr = db.Transactions.Where(x => x.CustomerId == item.Id).ToList();
                foreach (var itemtr in tr)
                {
                    item.Transactions.Add(itemtr);
                }
                //item.Transactions.AddRange(tr);
                rrr.Add(new CustomerReport()
                {
                    Name = item.Name,
                    VehicleNumber = item.VehicleNumber,
                    TotalAmount = item.Transactions.Sum(x => x.PetrolAmount + x.FreeAmount)
                });
            }
            return new Response<List<CustomerReport>>("success", rrr, "", 1);
        }
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool CustomerExists(int id)
        {
            return db.Customers.Count(e => e.Id == id) > 0;
        }
    }
}