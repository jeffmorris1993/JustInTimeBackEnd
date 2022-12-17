package com.justintime.cardetail.Util;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Constants {

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
        WWSP("J.I.T WASH & WAX (Speciality Paint)", 9, new BigDecimal(50), new BigDecimal(100));

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
        WA(new BigDecimal(50), new BigDecimal(100)),
        VD(new BigDecimal(30), new BigDecimal(30)),
        LC(new BigDecimal(30), new BigDecimal(30)),
        FSC(new BigDecimal(40), new BigDecimal(40)),
        UVPA(new BigDecimal(30), new BigDecimal(30)),
        FMS(new BigDecimal(50), new BigDecimal(50)),
        PHR(new BigDecimal(60), new BigDecimal(60)),
        CBT(new BigDecimal(50), new BigDecimal(100)),
        DV(new BigDecimal(25), new BigDecimal(50)),
        GT(new BigDecimal(20), new BigDecimal(20)),
        TDA(new BigDecimal(20), new BigDecimal(20)),
        OED(new BigDecimal(50), new BigDecimal(50)),
        SR(new BigDecimal(40), new BigDecimal(40)),
        HR(new BigDecimal(20), new BigDecimal(20)),
        HWSR(new BigDecimal(20), new BigDecimal(20)),
        TMR(new BigDecimal(20), new BigDecimal(20)),
        CS(new BigDecimal(125), new BigDecimal(125)),
        CCS(new BigDecimal(795), new BigDecimal(795));

        private final BigDecimal carSUVCost;
        private final BigDecimal truckVanCost;

        AddOnType(BigDecimal carSUVCost, BigDecimal truckVanCost) {
            this.carSUVCost = carSUVCost;
            this.truckVanCost = truckVanCost;
        }
    }
}
