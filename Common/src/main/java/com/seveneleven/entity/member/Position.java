package com.seveneleven.entity.member;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.member.constant.YN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "position")
public class Position extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false, referencedColumnName = "id")
    private Department department;

    @Column(name = "position_name", nullable = false, length = 50)
    private String positionName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN isActive = YN.Y;


    // 생성 메서드
    public static Position createPosition( Department department, String positionName, String description) {
        Position position = new Position();
        position.department   = department;
        position.description  = description;
        position.positionName = positionName;
        return position;
    }

    // 부서 설정 메서드
    public void setDepartment(Department department) {
        this.department = department;
    }

    // 삭제 메서드
    public void deletePostion() {
        this.isActive = YN.N;
    }

}
