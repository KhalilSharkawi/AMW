namespace API.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    //using System.Data.Entity.Spatial;

    [Table("Transaction")]
    public partial class Transaction
    {
        public int Id { get; set; }

        //public int CardId { get; set; }

        [Required]
        public int CustomerId { get; set; }
        [Required]
        public DateTime Date { get; set; }
        [Required]
        [DefaultValue(0)]
        public float PetrolAmount { get; set; }
        [Required]
        [DefaultValue(0)]
        public float FreeAmount { get; set; }
        [Required]
        [DefaultValue(0)]
        public float TotalPrice { get; set; }
        //{
        //    get
        //    {
        //        return PetrolAmount * 225 + FreeAmount * 425;
        //    }
        //}
        //[Required]
        //public TransactionType TransactionType { get; set; }
        public virtual Customer Customer { get; set; }
        public bool ConvertExceedingAmountToFree { get; set; }

        //public virtual SalesPerson SalesPerson { get; set; }
        public static float calculateTotalPrice(float PetrolAmount, float FreeAmount)
        {
            return PetrolAmount * 225 + FreeAmount * 425;
        }
    }

    public class TransactionModel
    {
        [Required]
        public int CustomerId { get; set; }
        [Required]
        public String Date { get; set; }
        [Required]
        [DefaultValue(0)]
        public float PetrolAmount { get; set; }
        [Required]
        [DefaultValue(0)]
        public float FreeAmount { get; set; }
        public bool ConvertExceedingAmountToFree { get; set; }
    }
}
