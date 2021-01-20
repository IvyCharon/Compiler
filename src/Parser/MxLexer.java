package Parser;

// Generated from C:/Users/skysz/Desktop/study/Computer_System/Compiler/Compiler/src/Parser\Mx.g4 by ANTLR 4.9
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, Int=5, Bool=6, String=7, Null=8, Void=9, 
		True=10, False=11, If=12, Else=13, For=14, While=15, Break=16, Continue=17, 
		Return=18, New=19, Class=20, This=21, Identifier=22, IntegerConstant=23, 
		StringConstant=24, Whitespace=25, Newline=26, BlockComment=27, LineComment=28, 
		Add=29, Sub=30, Mul=31, Div=32, Mod=33, Smallersmaller=34, Biggerbigger=35, 
		Smaller=36, Bigger=37, Smaller_equal=38, Bigger_equal=39, Equal=40, Not_equal=41, 
		And=42, Xor=43, Or=44, Andand=45, Oror=46, Plusplus=47, Subsub=48, Not=49, 
		Bit_opposite=50, LeftParen=51, RightParen=52, LeftBracket=53, RightBracket=54, 
		LeftBrace=55, RightBrace=56;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "Int", "Bool", "String", "Null", "Void", 
			"True", "False", "If", "Else", "For", "While", "Break", "Continue", "Return", 
			"New", "Class", "This", "Identifier", "IntegerConstant", "StringConstant", 
			"Whitespace", "Newline", "BlockComment", "LineComment", "Add", "Sub", 
			"Mul", "Div", "Mod", "Smallersmaller", "Biggerbigger", "Smaller", "Bigger", 
			"Smaller_equal", "Bigger_equal", "Equal", "Not_equal", "And", "Xor", 
			"Or", "Andand", "Oror", "Plusplus", "Subsub", "Not", "Bit_opposite", 
			"LeftParen", "RightParen", "LeftBracket", "RightBracket", "LeftBrace", 
			"RightBrace"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "','", "'='", "'.'", "'int'", "'bool'", "'string'", "'null'", 
			"'void'", "'true'", "'false'", "'if'", "'else'", "'for'", "'while'", 
			"'break'", "'continue'", "'return'", "'new'", "'class'", "'this'", null, 
			null, null, null, null, null, null, "'+'", "'-'", "'*'", "'/'", "'%'", 
			"'<<'", "'>>'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'&'", 
			"'^'", "'|'", "'&&'", "'||'", "'++'", "'--'", "'!'", "'~'", "'('", "')'", 
			"'['", "']'", "'{'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "Int", "Bool", "String", "Null", "Void", 
			"True", "False", "If", "Else", "For", "While", "Break", "Continue", "Return", 
			"New", "Class", "This", "Identifier", "IntegerConstant", "StringConstant", 
			"Whitespace", "Newline", "BlockComment", "LineComment", "Add", "Sub", 
			"Mul", "Div", "Mod", "Smallersmaller", "Biggerbigger", "Smaller", "Bigger", 
			"Smaller_equal", "Bigger_equal", "Equal", "Not_equal", "And", "Xor", 
			"Or", "Andand", "Oror", "Plusplus", "Subsub", "Not", "Bit_opposite", 
			"LeftParen", "RightParen", "LeftBracket", "RightBracket", "LeftBrace", 
			"RightBrace"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MxLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2:\u0162\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26"+
		"\3\26\3\26\3\26\3\27\3\27\7\27\u00da\n\27\f\27\16\27\u00dd\13\27\3\30"+
		"\3\30\7\30\u00e1\n\30\f\30\16\30\u00e4\13\30\3\30\5\30\u00e7\n\30\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u00f1\n\31\f\31\16\31\u00f4\13"+
		"\31\3\31\3\31\3\32\6\32\u00f9\n\32\r\32\16\32\u00fa\3\32\3\32\3\33\3\33"+
		"\5\33\u0101\n\33\3\33\5\33\u0104\n\33\3\33\3\33\3\34\3\34\3\34\3\34\7"+
		"\34\u010c\n\34\f\34\16\34\u010f\13\34\3\34\3\34\3\34\3\34\3\34\3\35\3"+
		"\35\3\35\3\35\7\35\u011a\n\35\f\35\16\35\u011d\13\35\3\35\3\35\3\36\3"+
		"\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\3&\3&\3\'\3"+
		"\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3.\3/\3/\3/"+
		"\3\60\3\60\3\60\3\61\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65"+
		"\3\66\3\66\3\67\3\67\38\38\39\39\4\u00f2\u010d\2:\3\3\5\4\7\5\t\6\13\7"+
		"\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O"+
		")Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:\3\2\b\4\2C\\c|\6\2"+
		"\62;C\\aac|\3\2\63;\3\2\62;\4\2\13\13\"\"\4\2\f\f\17\17\2\u016d\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2"+
		"%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61"+
		"\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2"+
		"\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I"+
		"\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2"+
		"\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2"+
		"\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o"+
		"\3\2\2\2\2q\3\2\2\2\3s\3\2\2\2\5u\3\2\2\2\7w\3\2\2\2\ty\3\2\2\2\13{\3"+
		"\2\2\2\r\177\3\2\2\2\17\u0084\3\2\2\2\21\u008b\3\2\2\2\23\u0090\3\2\2"+
		"\2\25\u0095\3\2\2\2\27\u009a\3\2\2\2\31\u00a0\3\2\2\2\33\u00a3\3\2\2\2"+
		"\35\u00a8\3\2\2\2\37\u00ac\3\2\2\2!\u00b2\3\2\2\2#\u00b8\3\2\2\2%\u00c1"+
		"\3\2\2\2\'\u00c8\3\2\2\2)\u00cc\3\2\2\2+\u00d2\3\2\2\2-\u00d7\3\2\2\2"+
		"/\u00e6\3\2\2\2\61\u00e8\3\2\2\2\63\u00f8\3\2\2\2\65\u0103\3\2\2\2\67"+
		"\u0107\3\2\2\29\u0115\3\2\2\2;\u0120\3\2\2\2=\u0122\3\2\2\2?\u0124\3\2"+
		"\2\2A\u0126\3\2\2\2C\u0128\3\2\2\2E\u012a\3\2\2\2G\u012d\3\2\2\2I\u0130"+
		"\3\2\2\2K\u0132\3\2\2\2M\u0134\3\2\2\2O\u0137\3\2\2\2Q\u013a\3\2\2\2S"+
		"\u013d\3\2\2\2U\u0140\3\2\2\2W\u0142\3\2\2\2Y\u0144\3\2\2\2[\u0146\3\2"+
		"\2\2]\u0149\3\2\2\2_\u014c\3\2\2\2a\u014f\3\2\2\2c\u0152\3\2\2\2e\u0154"+
		"\3\2\2\2g\u0156\3\2\2\2i\u0158\3\2\2\2k\u015a\3\2\2\2m\u015c\3\2\2\2o"+
		"\u015e\3\2\2\2q\u0160\3\2\2\2st\7=\2\2t\4\3\2\2\2uv\7.\2\2v\6\3\2\2\2"+
		"wx\7?\2\2x\b\3\2\2\2yz\7\60\2\2z\n\3\2\2\2{|\7k\2\2|}\7p\2\2}~\7v\2\2"+
		"~\f\3\2\2\2\177\u0080\7d\2\2\u0080\u0081\7q\2\2\u0081\u0082\7q\2\2\u0082"+
		"\u0083\7n\2\2\u0083\16\3\2\2\2\u0084\u0085\7u\2\2\u0085\u0086\7v\2\2\u0086"+
		"\u0087\7t\2\2\u0087\u0088\7k\2\2\u0088\u0089\7p\2\2\u0089\u008a\7i\2\2"+
		"\u008a\20\3\2\2\2\u008b\u008c\7p\2\2\u008c\u008d\7w\2\2\u008d\u008e\7"+
		"n\2\2\u008e\u008f\7n\2\2\u008f\22\3\2\2\2\u0090\u0091\7x\2\2\u0091\u0092"+
		"\7q\2\2\u0092\u0093\7k\2\2\u0093\u0094\7f\2\2\u0094\24\3\2\2\2\u0095\u0096"+
		"\7v\2\2\u0096\u0097\7t\2\2\u0097\u0098\7w\2\2\u0098\u0099\7g\2\2\u0099"+
		"\26\3\2\2\2\u009a\u009b\7h\2\2\u009b\u009c\7c\2\2\u009c\u009d\7n\2\2\u009d"+
		"\u009e\7u\2\2\u009e\u009f\7g\2\2\u009f\30\3\2\2\2\u00a0\u00a1\7k\2\2\u00a1"+
		"\u00a2\7h\2\2\u00a2\32\3\2\2\2\u00a3\u00a4\7g\2\2\u00a4\u00a5\7n\2\2\u00a5"+
		"\u00a6\7u\2\2\u00a6\u00a7\7g\2\2\u00a7\34\3\2\2\2\u00a8\u00a9\7h\2\2\u00a9"+
		"\u00aa\7q\2\2\u00aa\u00ab\7t\2\2\u00ab\36\3\2\2\2\u00ac\u00ad\7y\2\2\u00ad"+
		"\u00ae\7j\2\2\u00ae\u00af\7k\2\2\u00af\u00b0\7n\2\2\u00b0\u00b1\7g\2\2"+
		"\u00b1 \3\2\2\2\u00b2\u00b3\7d\2\2\u00b3\u00b4\7t\2\2\u00b4\u00b5\7g\2"+
		"\2\u00b5\u00b6\7c\2\2\u00b6\u00b7\7m\2\2\u00b7\"\3\2\2\2\u00b8\u00b9\7"+
		"e\2\2\u00b9\u00ba\7q\2\2\u00ba\u00bb\7p\2\2\u00bb\u00bc\7v\2\2\u00bc\u00bd"+
		"\7k\2\2\u00bd\u00be\7p\2\2\u00be\u00bf\7w\2\2\u00bf\u00c0\7g\2\2\u00c0"+
		"$\3\2\2\2\u00c1\u00c2\7t\2\2\u00c2\u00c3\7g\2\2\u00c3\u00c4\7v\2\2\u00c4"+
		"\u00c5\7w\2\2\u00c5\u00c6\7t\2\2\u00c6\u00c7\7p\2\2\u00c7&\3\2\2\2\u00c8"+
		"\u00c9\7p\2\2\u00c9\u00ca\7g\2\2\u00ca\u00cb\7y\2\2\u00cb(\3\2\2\2\u00cc"+
		"\u00cd\7e\2\2\u00cd\u00ce\7n\2\2\u00ce\u00cf\7c\2\2\u00cf\u00d0\7u\2\2"+
		"\u00d0\u00d1\7u\2\2\u00d1*\3\2\2\2\u00d2\u00d3\7v\2\2\u00d3\u00d4\7j\2"+
		"\2\u00d4\u00d5\7k\2\2\u00d5\u00d6\7u\2\2\u00d6,\3\2\2\2\u00d7\u00db\t"+
		"\2\2\2\u00d8\u00da\t\3\2\2\u00d9\u00d8\3\2\2\2\u00da\u00dd\3\2\2\2\u00db"+
		"\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc.\3\2\2\2\u00dd\u00db\3\2\2\2"+
		"\u00de\u00e2\t\4\2\2\u00df\u00e1\t\5\2\2\u00e0\u00df\3\2\2\2\u00e1\u00e4"+
		"\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e7\3\2\2\2\u00e4"+
		"\u00e2\3\2\2\2\u00e5\u00e7\7\62\2\2\u00e6\u00de\3\2\2\2\u00e6\u00e5\3"+
		"\2\2\2\u00e7\60\3\2\2\2\u00e8\u00f2\7$\2\2\u00e9\u00ea\7^\2\2\u00ea\u00f1"+
		"\7p\2\2\u00eb\u00ec\7^\2\2\u00ec\u00f1\7^\2\2\u00ed\u00ee\7^\2\2\u00ee"+
		"\u00f1\7$\2\2\u00ef\u00f1\13\2\2\2\u00f0\u00e9\3\2\2\2\u00f0\u00eb\3\2"+
		"\2\2\u00f0\u00ed\3\2\2\2\u00f0\u00ef\3\2\2\2\u00f1\u00f4\3\2\2\2\u00f2"+
		"\u00f3\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f3\u00f5\3\2\2\2\u00f4\u00f2\3\2"+
		"\2\2\u00f5\u00f6\7$\2\2\u00f6\62\3\2\2\2\u00f7\u00f9\t\6\2\2\u00f8\u00f7"+
		"\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb"+
		"\u00fc\3\2\2\2\u00fc\u00fd\b\32\2\2\u00fd\64\3\2\2\2\u00fe\u0100\7\17"+
		"\2\2\u00ff\u0101\7\f\2\2\u0100\u00ff\3\2\2\2\u0100\u0101\3\2\2\2\u0101"+
		"\u0104\3\2\2\2\u0102\u0104\7\f\2\2\u0103\u00fe\3\2\2\2\u0103\u0102\3\2"+
		"\2\2\u0104\u0105\3\2\2\2\u0105\u0106\b\33\2\2\u0106\66\3\2\2\2\u0107\u0108"+
		"\7\61\2\2\u0108\u0109\7,\2\2\u0109\u010d\3\2\2\2\u010a\u010c\13\2\2\2"+
		"\u010b\u010a\3\2\2\2\u010c\u010f\3\2\2\2\u010d\u010e\3\2\2\2\u010d\u010b"+
		"\3\2\2\2\u010e\u0110\3\2\2\2\u010f\u010d\3\2\2\2\u0110\u0111\7,\2\2\u0111"+
		"\u0112\7\61\2\2\u0112\u0113\3\2\2\2\u0113\u0114\b\34\2\2\u01148\3\2\2"+
		"\2\u0115\u0116\7\61\2\2\u0116\u0117\7\61\2\2\u0117\u011b\3\2\2\2\u0118"+
		"\u011a\n\7\2\2\u0119\u0118\3\2\2\2\u011a\u011d\3\2\2\2\u011b\u0119\3\2"+
		"\2\2\u011b\u011c\3\2\2\2\u011c\u011e\3\2\2\2\u011d\u011b\3\2\2\2\u011e"+
		"\u011f\b\35\2\2\u011f:\3\2\2\2\u0120\u0121\7-\2\2\u0121<\3\2\2\2\u0122"+
		"\u0123\7/\2\2\u0123>\3\2\2\2\u0124\u0125\7,\2\2\u0125@\3\2\2\2\u0126\u0127"+
		"\7\61\2\2\u0127B\3\2\2\2\u0128\u0129\7\'\2\2\u0129D\3\2\2\2\u012a\u012b"+
		"\7>\2\2\u012b\u012c\7>\2\2\u012cF\3\2\2\2\u012d\u012e\7@\2\2\u012e\u012f"+
		"\7@\2\2\u012fH\3\2\2\2\u0130\u0131\7>\2\2\u0131J\3\2\2\2\u0132\u0133\7"+
		"@\2\2\u0133L\3\2\2\2\u0134\u0135\7>\2\2\u0135\u0136\7?\2\2\u0136N\3\2"+
		"\2\2\u0137\u0138\7@\2\2\u0138\u0139\7?\2\2\u0139P\3\2\2\2\u013a\u013b"+
		"\7?\2\2\u013b\u013c\7?\2\2\u013cR\3\2\2\2\u013d\u013e\7#\2\2\u013e\u013f"+
		"\7?\2\2\u013fT\3\2\2\2\u0140\u0141\7(\2\2\u0141V\3\2\2\2\u0142\u0143\7"+
		"`\2\2\u0143X\3\2\2\2\u0144\u0145\7~\2\2\u0145Z\3\2\2\2\u0146\u0147\7("+
		"\2\2\u0147\u0148\7(\2\2\u0148\\\3\2\2\2\u0149\u014a\7~\2\2\u014a\u014b"+
		"\7~\2\2\u014b^\3\2\2\2\u014c\u014d\7-\2\2\u014d\u014e\7-\2\2\u014e`\3"+
		"\2\2\2\u014f\u0150\7/\2\2\u0150\u0151\7/\2\2\u0151b\3\2\2\2\u0152\u0153"+
		"\7#\2\2\u0153d\3\2\2\2\u0154\u0155\7\u0080\2\2\u0155f\3\2\2\2\u0156\u0157"+
		"\7*\2\2\u0157h\3\2\2\2\u0158\u0159\7+\2\2\u0159j\3\2\2\2\u015a\u015b\7"+
		"]\2\2\u015bl\3\2\2\2\u015c\u015d\7_\2\2\u015dn\3\2\2\2\u015e\u015f\7}"+
		"\2\2\u015fp\3\2\2\2\u0160\u0161\7\177\2\2\u0161r\3\2\2\2\r\2\u00db\u00e2"+
		"\u00e6\u00f0\u00f2\u00fa\u0100\u0103\u010d\u011b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}