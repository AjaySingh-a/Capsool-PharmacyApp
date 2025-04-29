// File: app/(tabs)/track-order.tsx

import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

export default function TrackOrderScreen() {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Track Order</Text>
      {/* TODO: Insert your Figma-based UI here */}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
    backgroundColor: '#F5F7F8',
    justifyContent: 'center',
  },
  title: {
    fontSize: 22,
    fontWeight: '700',
    textAlign: 'center',
    marginBottom: 16,
  },
});
