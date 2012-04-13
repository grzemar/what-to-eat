package ynd.whattoeat.utils;

public interface ContentLoaderCallback<T> {
	void contentLoaded(T result);

	void contentLoadingException(Exception e);
}
