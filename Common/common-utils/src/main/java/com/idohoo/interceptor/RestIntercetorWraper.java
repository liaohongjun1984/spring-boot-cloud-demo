package com.idohoo.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class RestIntercetorWraper implements RestInterceptor {

	protected List<String> includePaths = new ArrayList<String>();
	protected List<String> excludePaths = new ArrayList<String>();
	
	public String[] includePath() {
		String[] paths = this.includePaths.toArray(new String[this.includePaths.size()]);
		return paths;
	}

	public String[] excludePath() {
		String[] paths = this.excludePaths.toArray(new String[this.excludePaths.size()]);
		return paths;
	}

	public void addIncludePaths(String... paths) {
		this.includePaths.addAll(Arrays.asList(paths));
	}

	public void addExcludePaths(String... paths) {
		this.excludePaths.addAll(Arrays.asList(paths));
	}

}
