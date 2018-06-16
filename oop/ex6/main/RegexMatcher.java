package oop.ex6.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class implements an object that analyze a String variable according to some regex.
 */
public class RegexMatcher {

    /* an array of all the patterns to compare. */
    private String [] patterns;

    /* a instance because we don't want others to instantiate this object more than once per program. */
    private static final RegexMatcher regexMatcher = new RegexMatcher();

    /* a private constructor because we don't want others to instantiate on of those objects.  */
    private RegexMatcher() {

    }

    private Pattern pattern;

    private Matcher matcher;

    /**
     * @return : a singleton instance of this class.
     */
    public RegexMatcher getMatcher() {
        return regexMatcher;
    }

    public MatcherWrapper findMatch(String toMatch) {
        for (String regex: patterns) {
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(toMatch);
            if(matcher.matches()){
                break;
            }
        }
        //TODO need to fix this... add more logic after finding the right regex.
        return new MatcherWrapper;
    }

}
