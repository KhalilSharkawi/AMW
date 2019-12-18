﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebSite.Filters;
using WebSite.Models;

namespace WebSite.Controllers
{
    //[InitializeSimpleMembership]
    public class HomeController : Controller
    {
        private Model1 db = new Model1();
        public ActionResult Index()
        {
            ViewBag.Message = "By Students: Khalil Sharkawi & Zualfikar Ibraheem";
            ViewBag.CustomerId = new SelectList(db.Customers, "Id", "CardNumber");

            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your app description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }
    }
}
