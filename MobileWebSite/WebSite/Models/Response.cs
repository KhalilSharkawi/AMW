using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebSite.Models
{
    public class Response<T>
    {
        public Response()
        {
        }

        public Response(string result, T content, string errorDes, int errorCode)
        {
            this.result = result;
            this.content = content;
            this.errorDes = errorDes;
            this.errorCode = errorCode;
        }

        public string result { get; set; }

        public T content { get; set; }

        public string errorDes { get; set; }

        public int errorCode { get; set; }

    }
}