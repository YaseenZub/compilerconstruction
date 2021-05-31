package token;

/**
 * The {@code TokeType} enumeration represents types of tokens in subset of Java
 * language
 *
 * @author Ira Korshunova
 *
 */
public enum TokenType {
    BlockComment,

    LineComment,

    WhiteSpace,

    Tab,

    NewLine,

    CloseBrace,

    OpenBrace,

    OpeningCurlyBrace,

    ClosingCurlyBrace,

    DoubleConstant,

    IntConstant,

    Increment,
    Decrement,

    Plus,

    Minus,

    Multiply,

    Divide,

    Point,

    EqualEqual,

    Equal,
    For,

    ExclameEqual,

    Greater,

    Less,

    LessEqual,
    GreaterEqual,
    Static,

    Public,

    Private,
    Protected,

    Int,

    Double,

    Void,

    False,

    True,

    Null,

    Return,

    New,

    Class,

    If,

    While,

    Else_if,

    Else,


    Semicolon,



    InvertedComma,

    Comma,
    SingleComma,
    Identifier,
    String,
    Character;

    /**
     * Determines if this token is auxiliary
     *
     * @return {@code true} if token is auxiliary, {@code false} otherwise
     */
    public boolean isAuxiliary() {
        return this == BlockComment || this == LineComment || this == NewLine || this == Tab
                || this == WhiteSpace;
    }
}