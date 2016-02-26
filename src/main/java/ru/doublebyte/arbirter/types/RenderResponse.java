package ru.doublebyte.arbirter.types;

public class RenderResponse {

    private boolean success;
    private String message;
    private String url;

    ///////////////////////////////////////////////////////////////////////////

    public RenderResponse() {

    }

    public RenderResponse(boolean success, String message, String url) {
        this.success = success;
        this.message = message;
        this.url = url;
    }

    public String toString() {
        return String.format("RenderResponse[success=%b, message=%s, url=%s]",
                success, message, url);
    }

    ///////////////////////////////////////////////////////////////////////////

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
