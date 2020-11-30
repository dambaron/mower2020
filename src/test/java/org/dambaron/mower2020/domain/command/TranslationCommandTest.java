package org.dambaron.mower2020.domain.command;

class TranslationCommandTest {

//    @Test
//    void test_constructor() {
//
//    }
//
//    @ParameterizedTest
//    @NullAndEmptySource
//    void test_constructor_should_throw_exception_when_translation_code_is_blank(String translationCode) {
//        // given
//        // a null or empty translation code
//
//        // when
//        var thrown = catchThrowable(() -> new TranslationCommand(translationCode));
//
//        // then
//        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessage("Translation code must not be blank");
//    }
//
//    @ParameterizedTest
//    @EnumSource(Translation.class)
//    void test_toTranslation(Translation translation) {
//        // given
//        var translationCode = translation.getCode();
//        var translationCommand = new TranslationCommand(translationCode);
//
//        // when
//        var actualTranslation = translationCommand.toTranslation();
//
//        // then
//        var expectedTranslation = translation;
//
//        assertThat(actualTranslation).isEqualTo(expectedTranslation);
//    }
//
//    @Test
//    void test_toTranslation_should_throw_exception_when_translation_code_is_unknown() {
//        // given
//        var unknownTranslationCode = "unknownTranslationCode";
//        var translationCommand = new TranslationCommand(unknownTranslationCode);
//
//        // when
//        var thrown = catchThrowable(translationCommand::toTranslation);
//
//        // then
//        assertThat(thrown)
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("No enum constant %s.%s", Translation.class.getCanonicalName(), unknownTranslationCode);
//    }
}