package token;


import lexer.Lexer;

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
    Do,

    Increment,
    Decrement,
    CharacterClass,
    Plus,

    Minus,
    Implements,
    Extends,
    Multiply,

    Divide,
    Exception,
    Try,
    Catch,

    Point,

    EqualEqual,

    Equal,
    For,
    Default,
    ExclameEqual,

    Greater,
    Colon,
    Case,
    Switch,
    Less,

    LessEqual,
    GreaterEqual,
    Static,

    Public,
    Private,
    Protected,
    Final,
    Abstract,
    Interface,

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
    This,
    Super,

    Semicolon,
    OpenArray,
    CloseArray,
    StringClass,

    InvertedComma,

    Comma,
    SingleComma,
    Identifier,
    String,
    Character,
    EndMarker,

    Not,
    Or,

    And;

    public boolean isAuxiliary() {
        return this == BlockComment || this == LineComment || this == NewLine || this == Tab
                || this == WhiteSpace;
    }

    public static String toString(TokenType tokenType) {
        return ""+tokenType+"";
    }
}
interface A{

}