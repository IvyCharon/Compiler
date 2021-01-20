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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, Int=11, Bool=12, String=13, Null=14, Void=15, True=16, False=17, 
		If=18, Else=19, For=20, While=21, Break=22, Continue=23, Return=24, New=25, 
		Class=26, This=27, Identifier=28, BoolConstant=29, IntegerConstant=30, 
		StringConstant=31, NullConstant=32, Whitespace=33, Newline=34, BlockComment=35, 
		LineComment=36, Add=37, Sub=38, Mul=39, Div=40, Mod=41, Smallersmaller=42, 
		Biggerbigger=43, Smaller=44, Bigger=45, Smaller_equal=46, Bigger_equal=47, 
		Equal=48, Not_equal=49, And=50, Xor=51, Or=52, Andand=53, Oror=54, Plusplus=55, 
		Subsub=56, Not=57, Bit_opposite=58;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "Int", "Bool", "String", "Null", "Void", "True", "False", "If", 
			"Else", "For", "While", "Break", "Continue", "Return", "New", "Class", 
			"This", "Identifier", "BoolConstant", "IntegerConstant", "StringConstant", 
			"NullConstant", "Whitespace", "Newline", "BlockComment", "LineComment", 
			"Add", "Sub", "Mul", "Div", "Mod", "Smallersmaller", "Biggerbigger", 
			"Smaller", "Bigger", "Smaller_equal", "Bigger_equal", "Equal", "Not_equal", 
			"And", "Xor", "Or", "Andand", "Oror", "Plusplus", "Subsub", "Not", "Bit_opposite"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'('", "','", "')'", "';'", "'['", "']'", "'='", 
			"'.'", "'int'", "'bool'", "'string'", "'null'", "'void'", "'true'", "'false'", 
			"'if'", "'else'", "'for'", "'while'", "'break'", "'continue'", "'return'", 
			"'new'", "'class'", "'this'", null, null, null, null, null, null, null, 
			null, null, "'+'", "'-'", "'*'", "'/'", "'%'", "'<<'", "'>>'", "'<'", 
			"'>'", "'<='", "'>='", "'=='", "'!='", "'&'", "'^'", "'|'", "'&&'", "'||'", 
			"'++'", "'--'", "'!'", "'~'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "Int", 
			"Bool", "String", "Null", "Void", "True", "False", "If", "Else", "For", 
			"While", "Break", "Continue", "Return", "New", "Class", "This", "Identifier", 
			"BoolConstant", "IntegerConstant", "StringConstant", "NullConstant", 
			"Whitespace", "Newline", "BlockComment", "LineComment", "Add", "Sub", 
			"Mul", "Div", "Mod", "Smallersmaller", "Biggerbigger", "Smaller", "Bigger", 
			"Smaller_equal", "Bigger_equal", "Equal", "Not_equal", "And", "Xor", 
			"Or", "Andand", "Oror", "Plusplus", "Subsub", "Not", "Bit_opposite"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2<\u016c\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\3\2\3\2\3\3"+
		"\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\24"+
		"\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\34\3\34\3\34\3\34\3\34\3\35\3\35\7\35\u00ea\n\35\f\35\16\35\u00ed"+
		"\13\35\3\36\3\36\5\36\u00f1\n\36\3\37\3\37\7\37\u00f5\n\37\f\37\16\37"+
		"\u00f8\13\37\3\37\5\37\u00fb\n\37\3 \3 \3 \3 \3 \3 \3 \3 \7 \u0105\n "+
		"\f \16 \u0108\13 \3 \3 \3!\3!\3\"\6\"\u010f\n\"\r\"\16\"\u0110\3\"\3\""+
		"\3#\3#\5#\u0117\n#\3#\5#\u011a\n#\3#\3#\3$\3$\3$\3$\7$\u0122\n$\f$\16"+
		"$\u0125\13$\3$\3$\3$\3$\3$\3%\3%\3%\3%\7%\u0130\n%\f%\16%\u0133\13%\3"+
		"%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3+\3,\3,\3,\3-\3-\3.\3.\3/"+
		"\3/\3/\3\60\3\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\64\3\64"+
		"\3\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\38\38\38\39\39\39\3:\3:\3;\3"+
		";\4\u0106\u0123\2<\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r"+
		"\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63"+
		"e\64g\65i\66k\67m8o9q:s;u<\3\2\b\4\2C\\c|\6\2\62;C\\aac|\3\2\63;\3\2\62"+
		";\4\2\13\13\"\"\4\2\f\f\17\17\2\u0178\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2"+
		"\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2"+
		"\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3"+
		"\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3"+
		"\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65"+
		"\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3"+
		"\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2"+
		"\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2"+
		"[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3"+
		"\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2"+
		"\2\2u\3\2\2\2\3w\3\2\2\2\5y\3\2\2\2\7{\3\2\2\2\t}\3\2\2\2\13\177\3\2\2"+
		"\2\r\u0081\3\2\2\2\17\u0083\3\2\2\2\21\u0085\3\2\2\2\23\u0087\3\2\2\2"+
		"\25\u0089\3\2\2\2\27\u008b\3\2\2\2\31\u008f\3\2\2\2\33\u0094\3\2\2\2\35"+
		"\u009b\3\2\2\2\37\u00a0\3\2\2\2!\u00a5\3\2\2\2#\u00aa\3\2\2\2%\u00b0\3"+
		"\2\2\2\'\u00b3\3\2\2\2)\u00b8\3\2\2\2+\u00bc\3\2\2\2-\u00c2\3\2\2\2/\u00c8"+
		"\3\2\2\2\61\u00d1\3\2\2\2\63\u00d8\3\2\2\2\65\u00dc\3\2\2\2\67\u00e2\3"+
		"\2\2\29\u00e7\3\2\2\2;\u00f0\3\2\2\2=\u00fa\3\2\2\2?\u00fc\3\2\2\2A\u010b"+
		"\3\2\2\2C\u010e\3\2\2\2E\u0119\3\2\2\2G\u011d\3\2\2\2I\u012b\3\2\2\2K"+
		"\u0136\3\2\2\2M\u0138\3\2\2\2O\u013a\3\2\2\2Q\u013c\3\2\2\2S\u013e\3\2"+
		"\2\2U\u0140\3\2\2\2W\u0143\3\2\2\2Y\u0146\3\2\2\2[\u0148\3\2\2\2]\u014a"+
		"\3\2\2\2_\u014d\3\2\2\2a\u0150\3\2\2\2c\u0153\3\2\2\2e\u0156\3\2\2\2g"+
		"\u0158\3\2\2\2i\u015a\3\2\2\2k\u015c\3\2\2\2m\u015f\3\2\2\2o\u0162\3\2"+
		"\2\2q\u0165\3\2\2\2s\u0168\3\2\2\2u\u016a\3\2\2\2wx\7}\2\2x\4\3\2\2\2"+
		"yz\7\177\2\2z\6\3\2\2\2{|\7*\2\2|\b\3\2\2\2}~\7.\2\2~\n\3\2\2\2\177\u0080"+
		"\7+\2\2\u0080\f\3\2\2\2\u0081\u0082\7=\2\2\u0082\16\3\2\2\2\u0083\u0084"+
		"\7]\2\2\u0084\20\3\2\2\2\u0085\u0086\7_\2\2\u0086\22\3\2\2\2\u0087\u0088"+
		"\7?\2\2\u0088\24\3\2\2\2\u0089\u008a\7\60\2\2\u008a\26\3\2\2\2\u008b\u008c"+
		"\7k\2\2\u008c\u008d\7p\2\2\u008d\u008e\7v\2\2\u008e\30\3\2\2\2\u008f\u0090"+
		"\7d\2\2\u0090\u0091\7q\2\2\u0091\u0092\7q\2\2\u0092\u0093\7n\2\2\u0093"+
		"\32\3\2\2\2\u0094\u0095\7u\2\2\u0095\u0096\7v\2\2\u0096\u0097\7t\2\2\u0097"+
		"\u0098\7k\2\2\u0098\u0099\7p\2\2\u0099\u009a\7i\2\2\u009a\34\3\2\2\2\u009b"+
		"\u009c\7p\2\2\u009c\u009d\7w\2\2\u009d\u009e\7n\2\2\u009e\u009f\7n\2\2"+
		"\u009f\36\3\2\2\2\u00a0\u00a1\7x\2\2\u00a1\u00a2\7q\2\2\u00a2\u00a3\7"+
		"k\2\2\u00a3\u00a4\7f\2\2\u00a4 \3\2\2\2\u00a5\u00a6\7v\2\2\u00a6\u00a7"+
		"\7t\2\2\u00a7\u00a8\7w\2\2\u00a8\u00a9\7g\2\2\u00a9\"\3\2\2\2\u00aa\u00ab"+
		"\7h\2\2\u00ab\u00ac\7c\2\2\u00ac\u00ad\7n\2\2\u00ad\u00ae\7u\2\2\u00ae"+
		"\u00af\7g\2\2\u00af$\3\2\2\2\u00b0\u00b1\7k\2\2\u00b1\u00b2\7h\2\2\u00b2"+
		"&\3\2\2\2\u00b3\u00b4\7g\2\2\u00b4\u00b5\7n\2\2\u00b5\u00b6\7u\2\2\u00b6"+
		"\u00b7\7g\2\2\u00b7(\3\2\2\2\u00b8\u00b9\7h\2\2\u00b9\u00ba\7q\2\2\u00ba"+
		"\u00bb\7t\2\2\u00bb*\3\2\2\2\u00bc\u00bd\7y\2\2\u00bd\u00be\7j\2\2\u00be"+
		"\u00bf\7k\2\2\u00bf\u00c0\7n\2\2\u00c0\u00c1\7g\2\2\u00c1,\3\2\2\2\u00c2"+
		"\u00c3\7d\2\2\u00c3\u00c4\7t\2\2\u00c4\u00c5\7g\2\2\u00c5\u00c6\7c\2\2"+
		"\u00c6\u00c7\7m\2\2\u00c7.\3\2\2\2\u00c8\u00c9\7e\2\2\u00c9\u00ca\7q\2"+
		"\2\u00ca\u00cb\7p\2\2\u00cb\u00cc\7v\2\2\u00cc\u00cd\7k\2\2\u00cd\u00ce"+
		"\7p\2\2\u00ce\u00cf\7w\2\2\u00cf\u00d0\7g\2\2\u00d0\60\3\2\2\2\u00d1\u00d2"+
		"\7t\2\2\u00d2\u00d3\7g\2\2\u00d3\u00d4\7v\2\2\u00d4\u00d5\7w\2\2\u00d5"+
		"\u00d6\7t\2\2\u00d6\u00d7\7p\2\2\u00d7\62\3\2\2\2\u00d8\u00d9\7p\2\2\u00d9"+
		"\u00da\7g\2\2\u00da\u00db\7y\2\2\u00db\64\3\2\2\2\u00dc\u00dd\7e\2\2\u00dd"+
		"\u00de\7n\2\2\u00de\u00df\7c\2\2\u00df\u00e0\7u\2\2\u00e0\u00e1\7u\2\2"+
		"\u00e1\66\3\2\2\2\u00e2\u00e3\7v\2\2\u00e3\u00e4\7j\2\2\u00e4\u00e5\7"+
		"k\2\2\u00e5\u00e6\7u\2\2\u00e68\3\2\2\2\u00e7\u00eb\t\2\2\2\u00e8\u00ea"+
		"\t\3\2\2\u00e9\u00e8\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb"+
		"\u00ec\3\2\2\2\u00ec:\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00f1\5!\21\2"+
		"\u00ef\u00f1\5#\22\2\u00f0\u00ee\3\2\2\2\u00f0\u00ef\3\2\2\2\u00f1<\3"+
		"\2\2\2\u00f2\u00f6\t\4\2\2\u00f3\u00f5\t\5\2\2\u00f4\u00f3\3\2\2\2\u00f5"+
		"\u00f8\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00fb\3\2"+
		"\2\2\u00f8\u00f6\3\2\2\2\u00f9\u00fb\7\62\2\2\u00fa\u00f2\3\2\2\2\u00fa"+
		"\u00f9\3\2\2\2\u00fb>\3\2\2\2\u00fc\u0106\7$\2\2\u00fd\u00fe\7^\2\2\u00fe"+
		"\u0105\7p\2\2\u00ff\u0100\7^\2\2\u0100\u0105\7^\2\2\u0101\u0102\7^\2\2"+
		"\u0102\u0105\7$\2\2\u0103\u0105\13\2\2\2\u0104\u00fd\3\2\2\2\u0104\u00ff"+
		"\3\2\2\2\u0104\u0101\3\2\2\2\u0104\u0103\3\2\2\2\u0105\u0108\3\2\2\2\u0106"+
		"\u0107\3\2\2\2\u0106\u0104\3\2\2\2\u0107\u0109\3\2\2\2\u0108\u0106\3\2"+
		"\2\2\u0109\u010a\7$\2\2\u010a@\3\2\2\2\u010b\u010c\5\35\17\2\u010cB\3"+
		"\2\2\2\u010d\u010f\t\6\2\2\u010e\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110"+
		"\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0113\b\""+
		"\2\2\u0113D\3\2\2\2\u0114\u0116\7\17\2\2\u0115\u0117\7\f\2\2\u0116\u0115"+
		"\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u011a\3\2\2\2\u0118\u011a\7\f\2\2\u0119"+
		"\u0114\3\2\2\2\u0119\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011c\b#"+
		"\2\2\u011cF\3\2\2\2\u011d\u011e\7\61\2\2\u011e\u011f\7,\2\2\u011f\u0123"+
		"\3\2\2\2\u0120\u0122\13\2\2\2\u0121\u0120\3\2\2\2\u0122\u0125\3\2\2\2"+
		"\u0123\u0124\3\2\2\2\u0123\u0121\3\2\2\2\u0124\u0126\3\2\2\2\u0125\u0123"+
		"\3\2\2\2\u0126\u0127\7,\2\2\u0127\u0128\7\61\2\2\u0128\u0129\3\2\2\2\u0129"+
		"\u012a\b$\2\2\u012aH\3\2\2\2\u012b\u012c\7\61\2\2\u012c\u012d\7\61\2\2"+
		"\u012d\u0131\3\2\2\2\u012e\u0130\n\7\2\2\u012f\u012e\3\2\2\2\u0130\u0133"+
		"\3\2\2\2\u0131\u012f\3\2\2\2\u0131\u0132\3\2\2\2\u0132\u0134\3\2\2\2\u0133"+
		"\u0131\3\2\2\2\u0134\u0135\b%\2\2\u0135J\3\2\2\2\u0136\u0137\7-\2\2\u0137"+
		"L\3\2\2\2\u0138\u0139\7/\2\2\u0139N\3\2\2\2\u013a\u013b\7,\2\2\u013bP"+
		"\3\2\2\2\u013c\u013d\7\61\2\2\u013dR\3\2\2\2\u013e\u013f\7\'\2\2\u013f"+
		"T\3\2\2\2\u0140\u0141\7>\2\2\u0141\u0142\7>\2\2\u0142V\3\2\2\2\u0143\u0144"+
		"\7@\2\2\u0144\u0145\7@\2\2\u0145X\3\2\2\2\u0146\u0147\7>\2\2\u0147Z\3"+
		"\2\2\2\u0148\u0149\7@\2\2\u0149\\\3\2\2\2\u014a\u014b\7>\2\2\u014b\u014c"+
		"\7?\2\2\u014c^\3\2\2\2\u014d\u014e\7@\2\2\u014e\u014f\7?\2\2\u014f`\3"+
		"\2\2\2\u0150\u0151\7?\2\2\u0151\u0152\7?\2\2\u0152b\3\2\2\2\u0153\u0154"+
		"\7#\2\2\u0154\u0155\7?\2\2\u0155d\3\2\2\2\u0156\u0157\7(\2\2\u0157f\3"+
		"\2\2\2\u0158\u0159\7`\2\2\u0159h\3\2\2\2\u015a\u015b\7~\2\2\u015bj\3\2"+
		"\2\2\u015c\u015d\7(\2\2\u015d\u015e\7(\2\2\u015el\3\2\2\2\u015f\u0160"+
		"\7~\2\2\u0160\u0161\7~\2\2\u0161n\3\2\2\2\u0162\u0163\7-\2\2\u0163\u0164"+
		"\7-\2\2\u0164p\3\2\2\2\u0165\u0166\7/\2\2\u0166\u0167\7/\2\2\u0167r\3"+
		"\2\2\2\u0168\u0169\7#\2\2\u0169t\3\2\2\2\u016a\u016b\7\u0080\2\2\u016b"+
		"v\3\2\2\2\16\2\u00eb\u00f0\u00f6\u00fa\u0104\u0106\u0110\u0116\u0119\u0123"+
		"\u0131\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}