package com.example.documentspicker;

import java.util.ArrayList;
import android.Manifest;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * DocumentsPickerPlugin
 */
public class DocumentsPickerPlugin implements MethodCallHandler, PluginRegistry.ActivityResultListener {
  /**
   * Plugin registration.
   */

  private Activity activity;
  private Result result;

  private DocumentsPickerPlugin(Activity activity) {
    this.activity = activity;
  }

  private ArrayList<String> docPaths = new ArrayList<>();

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "documents_picker");
    DocumentsPickerPlugin plugin = new DocumentsPickerPlugin(registrar.activity());
    channel.setMethodCallHandler(plugin);
    registrar.addActivityResultListener(plugin);
  }

  private static final int REQUEST_READ_PERMISSION = 786;


  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("pickDocuments")) {

      if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);
      } else {
        this.result = result;
        FilePickerBuilder.getInstance().setMaxCount(1)
                .setActivityTheme(R.style.LibAppTheme)
                .pickFile(activity);
      }
    } else {
      result.notImplemented();
    }
  }

  @Override
  public boolean onActivityResult(int requestCode, int resultCode, Intent intent) {

    if (requestCode == FilePickerConst.REQUEST_CODE_DOC){
      if (intent != null)
        docPaths = intent.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS);
    
      this.result.success(docPaths);

      return true;
    }

    return false;
  }
}
