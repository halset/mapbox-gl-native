package com.mapbox.mapboxsdk.testapp.annotations;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.testapp.R;
import com.mapbox.mapboxsdk.testapp.activity.BaseActivityTest;
import com.mapbox.mapboxsdk.testapp.activity.espresso.EspressoTestActivity;
import com.mapbox.mapboxsdk.testapp.utils.TestConstants;

import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class MarkerTest extends BaseActivityTest {

  private Marker marker;

  @Override
  protected Class getActivityClass() {
    return EspressoTestActivity.class;
  }

  @Test
  @Ignore
  public void addMarkerTest() {
    validateTestSetup();
    assertEquals("Markers should be empty", 0, mapboxMap.getMarkers().size());

    MarkerOptions options = new MarkerOptions();
    options.setPosition(new LatLng());
    options.setSnippet(TestConstants.TEXT_MARKER_SNIPPET);
    options.setTitle(TestConstants.TEXT_MARKER_TITLE);

    onView(withId(R.id.mapView)).perform(new AddMarkerAction(mapboxMap, options));
    assertEquals("Markers sze should be 1, ", 1, mapboxMap.getMarkers().size());
    assertEquals("Marker id should be 0", 0, marker.getId());
    assertEquals("Marker target should match", new LatLng(), marker.getPosition());
    assertEquals("Marker snippet should match", TestConstants.TEXT_MARKER_SNIPPET, marker.getSnippet());
    assertEquals("Marker target should match", TestConstants.TEXT_MARKER_TITLE, marker.getTitle());
    mapboxMap.clear();
    assertEquals("Markers should be empty", 0, mapboxMap.getMarkers().size());
  }

  @Test
  @Ignore
  public void showInfoWindowTest() {
    validateTestSetup();

    final MarkerOptions options = new MarkerOptions();
    options.setPosition(new LatLng());
    options.setSnippet(TestConstants.TEXT_MARKER_SNIPPET);
    options.setTitle(TestConstants.TEXT_MARKER_TITLE);

    onView(withId(R.id.mapView)).perform(new AddMarkerAction(mapboxMap, options));
    onView(withId(R.id.mapView)).perform(new ShowInfoWindowAction(mapboxMap));
    onView(withText(TestConstants.TEXT_MARKER_TITLE)).check(matches(isDisplayed()));
    onView(withText(TestConstants.TEXT_MARKER_SNIPPET)).check(matches(isDisplayed()));
  }

  private class AddMarkerAction implements ViewAction {

    private MapboxMap mapboxMap;
    private MarkerOptions options;

    AddMarkerAction(MapboxMap map, MarkerOptions markerOptions) {
      mapboxMap = map;
      options = markerOptions;
    }

    @Override
    public Matcher<View> getConstraints() {
      return isDisplayed();
    }

    @Override
    public String getDescription() {
      return getClass().getSimpleName();
    }

    @Override
    public void perform(UiController uiController, View view) {
      marker = mapboxMap.addMarker(options);
    }
  }

  private class ShowInfoWindowAction implements ViewAction {

    private MapboxMap mapboxMap;

    ShowInfoWindowAction(MapboxMap map) {
      mapboxMap = map;
    }

    @Override
    public Matcher<View> getConstraints() {
      return isDisplayed();
    }

    @Override
    public String getDescription() {
      return getClass().getSimpleName();
    }

    @Override
    public void perform(UiController uiController, View view) {
      mapboxMap.selectMarker(marker);

    }
  }
}
