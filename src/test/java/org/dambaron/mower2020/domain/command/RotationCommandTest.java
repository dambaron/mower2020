package org.dambaron.mower2020.domain.command;

class RotationCommandTest {

//    @Test
//    void test_constructor() {
//
//    }
//
//    @ParameterizedTest
//    @NullAndEmptySource
//    void test_constructor_should_throw_exception_when_rotation_code_is_blank(String rotationCode) {
//        // given
//        // a null or empty rotation code
//
//        // when
//        var thrown = catchThrowable(() -> new RotationCommand(rotationCode));
//
//        // then
//        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessage("Rotation code must not be blank");
//    }
//
//    @ParameterizedTest
//    @EnumSource(Rotation.class)
//    void test_toRotation(Rotation rotation) {
//        // given
//        var rotationCode = rotation.getCode();
//        var rotationCommand = new RotationCommand(rotationCode);
//
//        // when
//        var actualRotation = rotationCommand.toRotation();
//
//        // then
//        var expectedRotation = rotation;
//
//        assertThat(actualRotation).isEqualTo(expectedRotation);
//    }
//
//    @Test
//    void test_toRotation_should_throw_exception_when_rotation_code_is_unknown() {
//        // given
//        var unknownRotationCode = "unknownRotationCode";
//        var rotationCommand = new RotationCommand(unknownRotationCode);
//
//        // when
//        var thrown = catchThrowable(rotationCommand::toRotation);
//
//        // then
//        assertThat(thrown)
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("No enum constant %s.%s", Rotation.class.getCanonicalName(), unknownRotationCode);
//    }
}