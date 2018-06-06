import 'dart:async';

import 'package:flutter/services.dart';

class DocumentsPicker {
  static const MethodChannel _channel =
      const MethodChannel('documents_picker');

  static Future<List<dynamic>> get pickDocuments async {
    final List<dynamic> docsPaths = await _channel.invokeMethod('pickDocuments');
    return docsPaths;
  }
}
