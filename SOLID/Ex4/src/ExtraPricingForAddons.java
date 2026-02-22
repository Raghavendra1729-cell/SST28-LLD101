interface ExtraPricingForAddons {
    double price();
}



class MessAddOn implements ExtraPricingForAddons {
    @Override public double price() { return 1000.0; }
}

class LaundryAddOn implements ExtraPricingForAddons {
    @Override public double price() { return 500.0; }
}

class GymAddOn implements ExtraPricingForAddons {
    @Override public double price() { return 300.0; }
}