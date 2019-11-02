package com.example.ieeezsb.Interfaces;

public interface OnBackPressed {
    /**
     * If you return true the back press will not be taken into activity, otherwise the Fragment will act naturally
     * @return true if your processing has priority if not false
     */
    boolean onBackPressed();

}
