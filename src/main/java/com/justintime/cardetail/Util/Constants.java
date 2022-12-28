package com.justintime.cardetail.Util;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    @Getter
    public enum ServiceType {
        CSR("Choose Service Requested", 1, BigDecimal.ZERO, BigDecimal.ZERO),
        WWE("J.I.T WASH & WAX (EXTERIOR ONLY)", 2, new BigDecimal(50), new BigDecimal(100)),
        BP("J.I.T BASIC PACKAGE (INTERIOR/EXTERIOR)", 3, new BigDecimal(100), new BigDecimal(130)),
        PP("J.I.T PREMIUM PACKAGE (INTERIOR/EXTERIOR)", 4, new BigDecimal(195), new BigDecimal(225)),
        PLP("J.I.T PLATINUM PACKAGE (INTERIOR/EXTERIOR)", 5, new BigDecimal(255), new BigDecimal(285)),
        IDP("J.I.T INTERIOR DETAIL PACKAGE", 6, new BigDecimal(75), new BigDecimal(90)),
        PEP("J.I.T PET PACKAGE (INTERIOR ONLY)", 7, new BigDecimal(60), new BigDecimal(60)),
        LSR("J.I.T LIGHT SWIRL REMOVER + WAX PACKAGE (EXTERIOR)",8, new BigDecimal(245), new BigDecimal(275)),
        FS("J.I.T Fleet Service", 9, new BigDecimal(100), new BigDecimal(100)),
        WWSP("J.I.T WASH & WAX (Speciality Paint)", 10, new BigDecimal(50), new BigDecimal(100));

        private static final Map<String, ServiceType> BY_LABEL = new HashMap<>();
        private static final Map<Integer, ServiceType> BY_ID = new HashMap<>();

        static {
            for (ServiceType e : values()) {
                BY_LABEL.put(e.label, e);
                BY_ID.put(e.id, e);
            }
        }

        private final String label;
        private final int id;
        private final BigDecimal carSUVCost;
        private final BigDecimal truckVanCost;

        ServiceType(String label, int id, BigDecimal carSUVCost, BigDecimal truckVanCost) {
            this.label = label;
            this.id = id;
            this.carSUVCost = carSUVCost;
            this.truckVanCost = truckVanCost;
        }

        public static BigDecimal getCarSUVCost(int id) {
            return valueOfId(id).carSUVCost;
        }

        public static BigDecimal getTruckVanCost(int id) {
            return valueOfId(id).truckVanCost;
        }

        public static ServiceType valueOfId(int number) {
            return BY_ID.get(number);
        }
    }

    @Getter
    public enum AddOnType {
        WA(new BigDecimal(50), new BigDecimal(100), "Wax Application (wash included)"),
        VD(new BigDecimal(30), new BigDecimal(30), "Vinyl Dressing"),
        LC(new BigDecimal(30), new BigDecimal(30), "Leather Conditioning"),
        FSC(new BigDecimal(40), new BigDecimal(40), "Fabric Steam Cleaning"),
        UVPA(new BigDecimal(30), new BigDecimal(30), "UV Protectant Application"),
        FMS(new BigDecimal(50), new BigDecimal(50), "Fabric and Mat Shampooing"),
        PHR(new BigDecimal(60), new BigDecimal(60), "Pet Hair Removal"),
        CBT(new BigDecimal(50), new BigDecimal(100), "Clay Bar Treatment (wash included)"),
        DV(new BigDecimal(25), new BigDecimal(50), "Deep Vacuuming"),
        GT(new BigDecimal(20), new BigDecimal(20), "Glass Treatment"),
        TDA(new BigDecimal(20), new BigDecimal(20), "Tire Dressing Application"),
        OED(new BigDecimal(50), new BigDecimal(50), "Odor Elimination/Deodorizer (2hr Process)"),
        SR(new BigDecimal(40), new BigDecimal(40), "Stain Removal"),
        HR(new BigDecimal(20), new BigDecimal(20), "Headlight Restoration"),
        HWSR(new BigDecimal(20), new BigDecimal(20), "Hard Water Spot Removal"),
        TMR(new BigDecimal(20), new BigDecimal(20), "Trim & Molding Restoration"),
        CS(new BigDecimal(125), new BigDecimal(125), "COVID-19 Sanitation"),
        CCS(new BigDecimal(795), new BigDecimal(795), "Ceramic Coating Service");

        private final BigDecimal carSUVCost;
        private final BigDecimal truckVanCost;
        private final String label;

        AddOnType(BigDecimal carSUVCost, BigDecimal truckVanCost, String label) {
            this.carSUVCost = carSUVCost;
            this.truckVanCost = truckVanCost;
            this.label = label;
        }
    }
}
