package hanghae99.ditto.member.domain;

import hanghae99.ditto.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Follow extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "from_member_id", nullable = false)
    Member fromMember;

    @ManyToOne
    @JoinColumn(name = "to_member_id", nullable = false)
    Member toMember;

}
