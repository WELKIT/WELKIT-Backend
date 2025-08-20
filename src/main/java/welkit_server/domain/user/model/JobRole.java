package welkit_server.domain.user.model;

public enum JobRole {

    PLANNING_STRATEGY("기획·전략"),
    LEGAL_ADMIN_GENERAL("법무·사무·총무"),
    HR("인사·HR"),
    ACCOUNTING_TAX("회계·세무"),
    MARKETING_AD_MD("마케팅·광고·MD"),
    AI_DEVELOP_DATA("AI·개발·데이터"),
    DESIGN("디자인"),
    LOGISTICS_FOREIGN_TRADE("물류·무역"),
    DRIVING_TRANSPORT_DELIVERY("운전·운송·배송"),
    SALES("영업"),
    CUSTOMER_SUPPORT_TM("고객상담·TM"),
    FINANCE_INSURANCE("금융·보험"),
    FOOD_BEVERAGE("식·음료"),
    CUSTOMER_SERVICE_RETAIL("고객서비스·리테일"),
    ENGINEERING_DESIGN("엔지니어링·설계"),
    MANUFACTURING_PRODUCTION("제조·생산"),
    EDUCATION("교육"),
    ARCHITECTURE_FACILITY("건축·시설"),
    MEDICAL_BIO("의료·바이오"),
    MEDIA_CULTURE_SPORTS("미디어·문화·스포츠"),
    PUBLIC_WELFARE("공공·복지");

    private final String description;

    JobRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
