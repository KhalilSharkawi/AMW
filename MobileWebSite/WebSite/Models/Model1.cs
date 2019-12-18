namespace WebSite.Models
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;


    public partial class Model1 : DbContext
    {
        public Model1()
            : base("name=DefaultConnection")
        {
            this.Configuration.LazyLoadingEnabled = false;
            this.Configuration.ProxyCreationEnabled = true;
            this.Configuration.AutoDetectChangesEnabled = true;
        }

        public virtual DbSet<Customer> Customers { get; set; }
        public virtual DbSet<Transaction> Transactions { get; set; }
        public virtual DbSet<UserProfile> UserProfiles { get; set; }
        //public virtual DbSet<SalesPerson> SalesPersons { get; set; }
        
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Customer>()
                    .HasMany(e => e.Transactions)
                    .WithRequired(e => e.Customer)
                    .HasForeignKey(e => e.CustomerId)
                    .WillCascadeOnDelete(true);
     
            //modelBuilder.Entity<Transaction>()
            //    .HasRequired(e=>e.Customer);
            //modelBuilder.Entity<Region>()
            //    .HasMany(e => e.SalePerMonths)
            //    .WithRequired(e => e.Region)
            //    .WillCascadeOnDelete(false);

            //modelBuilder.Entity<Region>()
            //    .HasMany(e => e.SalesPersons)
            //    .WithRequired(e => e.Region)
            //    .HasForeignKey(e => e.MainRegionId)
            //    .WillCascadeOnDelete(false);

            //modelBuilder.Entity<SalesPerson>()
            //    .HasMany(e => e.SalePerMonths)
            //    .WithRequired(e => e.SalesPerson)
            //    .WillCascadeOnDelete(false);
        }
    }
}
