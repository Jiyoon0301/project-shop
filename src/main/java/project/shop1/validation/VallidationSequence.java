package project.shop1.validation;

import jakarta.validation.GroupSequence;

@GroupSequence({NotBlankGroup.class,SizeCheckGroup.class,PatternCheckGroup.class}) //확인 순서 정해주기
public interface VallidationSequence {



}
