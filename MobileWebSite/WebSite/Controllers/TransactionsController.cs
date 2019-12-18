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
    public class TransactionsController : Controller
    {
        private Model1 db = new Model1();

        //
        // GET: /Transactions/

        public ActionResult Index()
        {
            if (!(WebSecurity.CurrentUserName.Equals("emp") || WebSecurity.CurrentUserName.Equals("admin")))
            {
                //WebSecurity.Logout();
                return RedirectToAction("Login", "Account");
            }
            var transactions = db.Transactions.Include(t => t.Customer);
            ViewBag.CustomerId = new SelectList(db.Customers, "Id", "CardNumber");

            return View(transactions.ToList());
        }

        //
        // GET: /Transactions/Details/5

        public ActionResult Details(int id = 0)
        {
            Transaction transaction = db.Transactions.Find(id);
            if (transaction == null)
            {
                return HttpNotFound();
            }
            return View(transaction);
        }

        //
        // GET: /Transactions/Create

        public ActionResult Create()
        {
            ViewBag.CustomerId = new SelectList(db.Customers, "Id", "CardNumber");
            return View();
        }

        //
        // POST: /Transactions/Create

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(Transaction transaction)
        {
            if (ModelState.IsValid)
            {
                ViewBag.CustomerId = new SelectList(db.Customers, "Id", "CardNumber");
                var _5days = DateTime.Today.AddDays(-5);
                var totalIn5Days = db.Transactions
                    .Where(x => x.CustomerId == transaction.CustomerId
                        && x.Date > _5days)
                    .ToList().Sum(x => x.PetrolAmount);

                var totalInMonth = db.Transactions
                    .Where(x => x.CustomerId == transaction.CustomerId
                        && x.Date.Month == DateTime.Today.Month)
                    .ToList().Sum(x => x.PetrolAmount);

                if (totalIn5Days + transaction.PetrolAmount > 40 && transaction.ConvertExceedingAmountToFree)
                {
                    transaction.FreeAmount = totalIn5Days + transaction.PetrolAmount - 40;
                    transaction.PetrolAmount = transaction.PetrolAmount - transaction.FreeAmount;
                    
                }
                else if (totalIn5Days + transaction.PetrolAmount > 40)
                {
                    ModelState.AddModelError("PetrolAmount", "5 Days Amount Exceeded");
                    return View(transaction);
                }

                if (totalInMonth + transaction.PetrolAmount > 100 && transaction.ConvertExceedingAmountToFree)
                {
                    transaction.FreeAmount = totalIn5Days + transaction.PetrolAmount - 100;
                    transaction.PetrolAmount = transaction.PetrolAmount - transaction.FreeAmount;
                    
                }
                else if((totalInMonth + transaction.PetrolAmount > 100))
                {
                    ModelState.AddModelError("PetrolAmount", "Monthly Amount Exceeded");
                    return View(transaction);
                }
                transaction.TotalPrice = Transaction.calculateTotalPrice(transaction.PetrolAmount, transaction.FreeAmount);
                db.Transactions.Add(transaction);
                db.SaveChanges();
               
                return RedirectToAction("Index");
            }

            ViewBag.CustomerId = new SelectList(db.Customers, "Id", "Name", transaction.CustomerId);
            return View(transaction);
        }

        //
        // GET: /Transactions/Edit/5

        public ActionResult Edit(int id = 0)
        {
            Transaction transaction = db.Transactions.Find(id);
            if (transaction == null)
            {
                return HttpNotFound();
            }
            ViewBag.CustomerId = new SelectList(db.Customers, "Id", "Name", transaction.CustomerId);
            return View(transaction);
        }

        //
        // POST: /Transactions/Edit/5

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(Transaction transaction)
        {
            if (ModelState.IsValid)
            {
                db.Entry(transaction).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.CustomerId = new SelectList(db.Customers, "Id", "Name", transaction.CustomerId);
            return View(transaction);
        }

        //
        // GET: /Transactions/Delete/5

        public ActionResult Delete(int id = 0)
        {
            Transaction transaction = db.Transactions.Find(id);
            if (transaction == null)
            {
                return HttpNotFound();
            }
            return View(transaction);
        }

        //
        // POST: /Transactions/Delete/5

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Transaction transaction = db.Transactions.Find(id);
            db.Transactions.Remove(transaction);
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