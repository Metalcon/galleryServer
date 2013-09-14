package de.metalcon.sdd;

public enum Detail {

    FULL,
    // -------------
    SYMBOL,
    LINE,
    PARAGRAPH,
    PROFILE,
    TOOLTIP,
    SEARCH_ENTRY,
    SEARCH_DETAILED,
    // ------------
    NONE;
    
    public static Detail stringToEnum(String detail) {
        switch (detail) {
            case "full":                return FULL;
            case "symbol":              return SYMBOL;
            case "line":                return LINE;
            case "paragraph":           return PARAGRAPH;
            case "profile":             return PROFILE;
            case "tooltip":             return TOOLTIP;
            case "search_entry":        return SEARCH_ENTRY;
            case "search_detailed":     return SEARCH_DETAILED;
            
            case "none":
            default:
                return NONE;
        }
    }
    
    public static String enumToString(Detail detail) {
        switch (detail) {
            case FULL:                  return "full";
            case SYMBOL:                return "symbol";
            case LINE:                  return "line";
            case PARAGRAPH:             return "paragraph";
            case PROFILE:               return "profile";
            case TOOLTIP:               return "tooltip";
            case SEARCH_ENTRY:          return "search_entry";
            case SEARCH_DETAILED:       return "search_detailed";
            
            case NONE:
            default:
                return "none";
        }
    }
    
}