package com.papteco.web.beans;

import java.io.Serializable;

public enum ActionEnum implements Serializable {

	notApplicable,
	keyedInByUser,
	templateToOpened,
	autoFillCanOverwrite,
	autoFillReadOnly
}
