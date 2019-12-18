using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebMatrix.WebData;
using WebSite.Models;

namespace WebSite.Controllers
{
    public class CustomersController : Controller
    {
        private Model1 db = new Model1();

        //
        // GET: /Customers/

        public ActionResult Index()
        {
            if (!WebSecurity.CurrentUserName.Equals("admin"))
            {
                //WebSecurity.Logout();
                return RedirectToAction("Login", "Account");
            }
            return View(db.Customers.ToList());
        }
        [HttpGet]
        public ActionResult GetOne(int id = 0)
        {
            return Json(new { Customer = db.Customers.Find(id) }, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public ActionResult GetInfo(int id = 0)
        {
            var c = db.Customers.Find(id);
            var _5day = DateTime.Today.AddDays(-5);
            var _5daysAllownce = 40 - db.Transactions.Where(x => x.CustomerId == c.Id && x.Date >= _5day).Select(x => x.PetrolAmount).DefaultIfEmpty(0).Sum();
            var _monthlyAllownce = 100 - db.Transactions.Where(x => x.CustomerId == c.Id && x.Date.Month == DateTime.Now.Month).Select(x => x.PetrolAmount).DefaultIfEmpty(0).Sum();
            var free = db.Transactions.Where(x => x.CustomerId == c.Id).Select(x=>x.FreeAmount).DefaultIfEmpty(0).Sum();
            return Json(new
            {
                _5daysAllownce = _5daysAllownce,
                _monthlyAllownce = _monthlyAllownce,
                _free = free
            }, JsonRequestBehavior.AllowGet);
        }
        public ActionResult Report()
        {
            var result = db.Customers.ToList();
            foreach (var item in result)
            {
                var tr = db.Transactions.Where(x => x.CustomerId == item.Id).ToList();
                foreach (var itemtr in tr)
                {
                    item.Transactions.Add(itemtr);
                }
                //item.Transactions.AddRange(tr);
            }
            return View(result);
        }
        //
        // GET: /Customers/Details/5

        public ActionResult Details(int id = 0)
        {
            Customer customer = db.Customers.Find(id);
            if (customer == null)
            {
                return HttpNotFound();
            }
            return View(customer);
        }

        //
        // GET: /Customers/Create

        public ActionResult Create()
        {
            return View();
        }

        //
        // POST: /Customers/Create

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(Customer customer)
        {
            if (ModelState.IsValid)
            {
                db.Customers.Add(customer);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(customer);
        }

        //
        // GET: /Customers/Edit/5

        public ActionResult Edit(int id = 0)
        {
            Customer customer = db.Customers.Find(id);
            if (customer == null)
            {
                return HttpNotFound();
            }
            return View(customer);
        }

        //
        // POST: /Customers/Edit/5

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(Customer customer)
        {
            if (ModelState.IsValid)
            {
                db.Entry(customer).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(customer);
        }

        //
        // GET: /Customers/Delete/5

        public ActionResult Delete(int id = 0)
        {
            Customer customer = db.Customers.Find(id);
            if (customer == null)
            {
                return HttpNotFound();
            }
            return View(customer);
        }

        //
        // POST: /Customers/Delete/5

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Customer customer = db.Customers.Find(id);
            db.Customers.Remove(customer);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}