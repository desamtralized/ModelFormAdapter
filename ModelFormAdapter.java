package com.sambarboza;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.lang.reflect.Field;

public class ModelFormAdapter {

	private Object model;
	private View formContainer;

	public ModelFormAdapter(Object model, View formContainer) throws IllegalAccessException {
		this.model = model;
		this.formContainer = formContainer;
		this.updateFormValues();
	}

	/**
	 * Get model fields names and searches for view with respective tag names and set
	 * its values trying to cast property value to String
	 * @throws IllegalAccessException
	 */
	public void updateFormValues() throws IllegalAccessException, NullPointerException {
		Field[] modelFields = this.model.getClass().getDeclaredFields();
		for (Field field:modelFields) {
			field.setAccessible(true);
			String propName = field.getName();
			View formField = this.formContainer.findViewWithTag(propName);
			if (formField != null && CheckBox.class.isAssignableFrom(formField.getClass())) {
				CheckBox checkBox = (CheckBox) formField;
				Boolean checked;
				try {
					checked = (Boolean) field.get(this.model);
				} catch (ClassCastException e) {
					checked = false;
				}
				checkBox.setChecked(checked);
			} else if (formField != null && TextView.class.isAssignableFrom(formField.getClass())) {
				TextView txtView = (TextView) formField;
				String propValue;
				try {
					propValue = (String) field.get(this.model);
				} catch (ClassCastException e) {
					propValue = field.get(this.model).toString();
				}
				if (txtView != null) {
					txtView.setText(propValue);
				}
			}
		}
	}

	/**
	 * Get forms fields values and updates model
	 */
	public void updateModelValues() throws IllegalAccessException, ClassCastException  {
		Field[] modelFields = this.model.getClass().getDeclaredFields();
		for (Field field:modelFields) {
			field.setAccessible(true);
			String propName = field.getName();
			View formField = this.formContainer.findViewWithTag(propName);
			if (formField != null && CheckBox.class.isAssignableFrom(formField.getClass())) {
				CheckBox checkBox = (CheckBox) formField;
				field.set(this.model, field.getType().cast(checkBox.isChecked()));
			} else if (formField != null && TextView.class.isAssignableFrom(formField.getClass())) {
				TextView txtView = (TextView) formField;
				field.set(this.model, field.getType().cast(txtView.getText().toString()));
			}
		}
	}

	/**
	 * Returns the model object
	 */
	public Object getModel() {
		return this.model;
	}

	/**
	 * Returns the form view container
	 */
	public View getFormContainer() {
		return this.formContainer;
	}

}