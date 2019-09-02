import 'dart:async';

import 'package:flutter/services.dart';

class DocumentsPicker {
  static const MethodChannel _channel =
      const MethodChannel('documents_picker');

  static Future<List<String>> get pickDocuments async {
    final List<dynamic> docsPaths = await _channel.invokeMethod('pickDocuments');
    if (docsPaths != null && docsPaths.isEmpty) return [];
    return docsPaths?.cast<String>();
  }
}
