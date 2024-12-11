package org.acme.entity.constant;

public enum SeatGrade {



    R  ("R석", "무대와의 거리가 가까워 생동감 있게 관람할 수 있는 자리입니다."),
    S  ("S석", "R석보다 약간 뒤쪽이나 옆쪽에 배치됩니다. 시야가 좋으면서도 비교적 합리적인 가격입니다."),
    A("A석/B석", "무대와 거리가 멀거나 측면에 위치하며, 가격이 상대적으로 저렴합니다.");

    private final String name;
    private final String description;




    SeatGrade(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }



    public static SeatGrade getSeatGradeByFloor(int floor) {
        return switch (floor) {
            case 1 -> SeatGrade.R;
            case 3 -> SeatGrade.S;
            default -> // 기본적으로 A 또는 B석으로 처리
                SeatGrade.A;
        };
    }
}


