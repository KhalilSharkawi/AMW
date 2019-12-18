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

namespace AWebSite.API
{
    //[RoutePrefix("api/transactions")]
    public class TransactionsController : ApiController
    {
        private Model1 db = new Model1();

        //[Route("all")]
        [HttpPost]
        public Response<List<Transaction>> all()
        {
            return new Response<List<Transaction>>("success", db.Transactions.ToList(), "", 1);
        }

        //// GET: api/Transactions/5
        //[ResponseType(typeof(Transaction))]
        //[Route("one")]
        [HttpPost]
        public Response<Transaction> one(int id)
        {
            Transaction Transaction = db.Transactions.Find(id);
            if (Transaction == null)
            {
                return new Response<Transaction>("Not Found", null, "Not Found", -1);
            }

            return new Response<Transaction>("success", Transaction, "", 1);
        }

        // PUT: api/Transactions/5
        //[ResponseType(typeof(void))]
        [HttpPost]
        //[Route("edit")]
        public Response<Transaction> edit([FromBody]Transaction Transaction)
        {
            if (!ModelState.IsValid)
            {
                return new Response<Transaction>("Bad Request", null, "Bad Request", -2);
            }

            db.Entry(Transaction).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TransactionExists(Transaction.Id))
                {
                    return new Response<Transaction>("Not Found", null, "Not Found", -1);
                }
                else
                {
                    throw;
                }
            }

            return new Response<Transaction>("success", Transaction, "", 1);
        }

        // POST: api/Transactions
        //[ResponseType(typeof(Transaction))]
        //[Route("create")]
        [HttpPost]
        public Response<Transaction> create([FromBody]Transaction transaction)
        {
            if (!ModelState.IsValid)
            {
                return new Response<Transaction>("Bad Request", null, "Bad Request", -2);
            }

            var _5days = DateTime.Today.AddDays(-5);
            var totalIn5Days = db.Transactions
                .Where(x => x.CustomerId == transaction.CustomerId
                    && x.Date > _5days)
                .ToList().Sum(x => x.PetrolAmount);

            var totalInMonth = db.Transactions
                .Where(x => x.CustomerId == transaction.CustomerId
                    && x.Date.Month == DateTime.Today.Month)
                .ToList().Sum(x => x.PetrolAmount);

            if (totalIn5Days + transaction.PetrolAmount >= 40 && transaction.ConvertExceedingAmountToFree)
            {
                transaction.FreeAmount = totalIn5Days + transaction.PetrolAmount - 40;
                transaction.PetrolAmount = transaction.PetrolAmount - transaction.FreeAmount;
                transaction.TotalPrice = Transaction.calculateTotalPrice(transaction.PetrolAmount, transaction.FreeAmount);
                db.Transactions.Add(transaction);
                db.SaveChanges();
            }
            else if (totalIn5Days + transaction.PetrolAmount >= 40)
            {
                ModelState.AddModelError("PetrolAmount", "5 Days Amount Exceeded");
                return new Response<Transaction>("Inernal Server Error", null, "5 Days Amount Exceeded", -2);
            }

            if (totalInMonth + transaction.PetrolAmount >= 100 && transaction.ConvertExceedingAmountToFree)
            {
                transaction.FreeAmount = totalIn5Days + transaction.PetrolAmount - 100;
                transaction.PetrolAmount = transaction.PetrolAmount - transaction.FreeAmount;
                transaction.TotalPrice = Transaction.calculateTotalPrice(transaction.PetrolAmount, transaction.FreeAmount);
                db.Transactions.Add(transaction);
                db.SaveChanges();
            }
            else if ((totalInMonth + transaction.PetrolAmount >= 100))
            {
                ModelState.AddModelError("PetrolAmount", "Monthly Amount Exceeded");
                return new Response<Transaction>("Inernal Server Error", null, "Monthly Amount Exceeded", -2);
            }
           

            return new Response<Transaction>("success", transaction, "", 1);
        }

        // DELETE: api/Transactions/5
        //[ResponseType(typeof(void))]
        [HttpPost]
        //[Route("delete")]
        public Response<Transaction> delete([FromBody]Transaction reg)
        {
            Transaction Transaction = db.Transactions.Find(reg.Id);
            if (Transaction == null)
            {
                return new Response<Transaction>("Not Found", null, "Not Found", -1);
            }

            db.Transactions.Remove(Transaction);
            db.SaveChanges();

            return new Response<Transaction>("success", null, "", 1);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool TransactionExists(int id)
        {
            return db.Transactions.Count(e => e.Id == id) > 0;
        }
    }
}