namespace API.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    //using System.Data.Entity.Spatial;

    [Table("Customer")]
    public partial class Customer
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Customer()
        {
            Transactions = new HashSet<Transaction>();
            //SalesPersons = new HashSet<SalesPerson>();
        }

        public int Id { get; set; }

        [Required]
        [StringLength(256)]
        public string Name { get; set; }
        [Required]
        [StringLength(256)]
        public string MobileNumber { get; set; }
        [Required]
        [StringLength(256)]
        public string NationalNumber { get; set; }
        [Required]
        [StringLength(256)]
        public string Residence { get; set; }
        [Required]
        [StringLength(256)]
        public string Address { get; set; }
        [Required]
        [StringLength(256)]
        public string CardNumber { get; set; }
        [Required]
        [StringLength(256)]
        public string VehicleNumber { get; set; }
        //public virtual UserProfile User { get; set; }

        //[Required]
        //public int UserId { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Transaction> Transactions { get; set; }

        //[System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        //public virtual ICollection<SalesPerson> SalesPersons { get; set; }
    }

    public class CustomerInfo
    {
        public float _5daysAllownce { get; set; }
        public float _monthlyAllownce { get; set; }
        public float _free { get; set; }
    }

    public class CustomerReport
    {
        public string Name { get; set; }
        public string VehicleNumber { get; set; }
        public float TotalAmount { get; set; }
    }
}
