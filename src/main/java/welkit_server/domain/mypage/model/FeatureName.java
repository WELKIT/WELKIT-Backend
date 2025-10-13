package welkit_server.domain.mypage.model;
import lombok.Getter;

@Getter
public enum FeatureName {
    INSIGHT_CARDS("/cards"),
    TERMS("/terms"),
    RETROSPECTIVES("/retrospectives");

    private final String pathPrefix;

    FeatureName(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public static FeatureName fromUrl(String uri) {
        for (FeatureName feature : values()) {
            if (uri.startsWith(feature.getPathPrefix())) {
                return feature;
            }
        }
        return null;
    }

}
