import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Switch,
  ScrollView,
  Image,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';

export default function HomeScreen() {
  const [isOnline, setIsOnline] = useState(true);

  const orders = [
    {
      type: 'Urgent',
      number: 8294,
      timer: '3:00',
      customer: 'Sarah Thompson',
      meds: ['Paracetamol 650mg (x10)', 'Amoxicillin 500mg (x20)'],
      address: '742 Evergreen Terrace, Springfield',
      phone: '7574258543',
    },
    {
      type: 'Regular',
      number: 8295,
      timer: '3:00',
      customer: 'John Doe',
      meds: ['Ibuprofen 200mg (x5)'],
      address: '123 Main St, Metropolis',
      phone: '1234567890',
    },
  ];

  const inProgress = [
    {
      type: 'Urgent',
      number: 8296,
      elapsed: '2 mins ago',
      customer: 'Alice Smith',
      meds: ['Cetirizine 10mg (x15)'],
      address: '456 Oak Ave, Smallville',
      phone: '9876543210',
    },
  ];

  return (
    <ScrollView style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <Ionicons name="person-circle-outline" size={28} color="#000" />
        <Image source={require('@/assets/images/logo.png')} style={styles.logo} />
        <View style={styles.toggleRow}>
          <Text style={styles.onlineText}>
            {isOnline ? 'Online' : 'Offline'}
          </Text>
          <Switch
            value={isOnline}
            onValueChange={() => setIsOnline(prev => !prev)}
          />
        </View>
      </View>

      {isOnline ? (
        // --- ONLINE UI ---
        <>
          {/* Today's Summary */}
          <View style={styles.summaryCard}>
            <View>
              <Text style={styles.summaryTitle}>Today's Summary</Text>
              <Text style={styles.statValue}>₹342</Text>
              <Text style={styles.statLabel}>Earnings</Text>
            </View>
            <View>
              <Text style={styles.statValue}>15</Text>
              <Text style={styles.statLabel}>Orders</Text>
            </View>
            <View>
              <Text style={styles.statValue}>4.8</Text>
              <Text style={styles.statLabel}>Rating</Text>
            </View>
            <Text style={styles.summaryDate}>March 15, 2024</Text>
          </View>

          {/* New Orders */}
          <View style={styles.sectionHeader}>
            <Text style={styles.sectionTitle}>New Orders</Text>
            <Ionicons name="notifications-outline" size={24} />
          </View>
          {orders.map((o, i) => (
            <View key={i} style={styles.orderCard}>
              <View
                style={[
                  styles.badge,
                  o.type === 'Urgent' ? styles.urgent : styles.regular,
                ]}>
                <Text style={styles.badgeText}>{o.type}</Text>
              </View>

              <View style={styles.orderRow}>
                <Text style={styles.orderNumber}>Order #{o.number}</Text>
                <Text style={styles.timer}>{o.timer}</Text>
              </View>
              <Text style={styles.customer}>{o.customer}</Text>

              <View style={styles.row}>
                <Ionicons name="pills" size={16} />
                <Text style={styles.medsLabel}> Medications:</Text>
              </View>
              {o.meds.map((m, mi) => (
                <Text key={mi} style={styles.medsText}>
                  - {m}
                </Text>
              ))}

              <View style={styles.row}>
                <Ionicons name="location-outline" size={16} />
                <Text style={styles.infoText}> {o.address}</Text>
              </View>
              <View style={styles.row}>
                <Ionicons name="call-outline" size={16} />
                <Text style={styles.infoText}> {o.phone}</Text>
              </View>

              <View style={styles.buttonRow}>
                <View style={styles.bidButton}>
                  <Text style={styles.bidText}>Bid</Text>
                </View>
                <View style={styles.rejectButton}>
                  <Text style={styles.rejectText}>Reject</Text>
                </View>
              </View>
            </View>
          ))}

          {/* In Progress */}
          <Text style={[styles.sectionTitle, { marginTop: 16 }]}>
            In Progress
          </Text>
          {inProgress.map((p, pi) => (
            <View key={pi} style={styles.orderCard}>
              <View
                style={[
                  styles.badge,
                  p.type === 'Urgent' ? styles.urgent : styles.regular,
                ]}>
                <Text style={styles.badgeText}>{p.type}</Text>
              </View>

              <View style={styles.orderRow}>
                <Text style={styles.orderNumber}>Order #{p.number}</Text>
                <Text style={styles.timer}>{p.elapsed}</Text>
              </View>
              <Text style={styles.customer}>{p.customer}</Text>

              <View style={styles.row}>
                <Ionicons name="pills" size={16} />
                <Text style={styles.medsLabel}> Medications:</Text>
              </View>
              {p.meds.map((m, mi) => (
                <Text key={mi} style={styles.medsText}>
                  - {m}
                </Text>
              ))}

              <View style={styles.row}>
                <Ionicons name="location-outline" size={16} />
                <Text style={styles.infoText}> {p.address}</Text>
              </View>
              <View style={styles.row}>
                <Ionicons name="call-outline" size={16} />
                <Text style={styles.infoText}> {p.phone}</Text>
              </View>

              <View style={styles.buttonRow}>
                <View style={styles.bidButton}>
                  <Text style={styles.bidText}>Pick Up</Text>
                </View>
                <View style={styles.rejectButton}>
                  <Text style={styles.rejectText}>Send Note</Text>
                </View>
              </View>
            </View>
          ))}
        </>
      ) : (
        // --- OFFLINE UI + exact Today's Summary ---
        <>
        <View style={styles.summaryCard}>
            <View>
              <Text style={styles.summaryTitle}>Today's Summary</Text>
              <Text style={styles.statValue}>₹342</Text>
              <Text style={styles.statLabel}>Earnings</Text>
            </View>
            <View>
              <Text style={styles.statValue}>15</Text>
              <Text style={styles.statLabel}>Orders</Text>
            </View>
            <View>
              <Text style={styles.statValue}>4.8</Text>
              <Text style={styles.statLabel}>Rating</Text>
            </View>
            <Text style={styles.summaryDate}>March 15, 2024</Text>
          </View>
          <View style={styles.offlineContainer}>
            <Image
              source={require('@/assets/images/offline.png')}
              style={styles.offlineImage}
            />
            <Text style={styles.offlineText}>You are offline</Text>
          </View>
        </>
      )}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 16, backgroundColor: '#F2F2F2' },
  header: { flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between' },
  logo: { width: 32, height: 32 },
  toggleRow: { flexDirection: 'row', alignItems: 'center', gap: 8 },
  onlineText: { marginRight: 8, fontWeight: '600' },

  summaryCard: {
    backgroundColor: '#fff',
    borderRadius: 12,
    padding: 16,
    marginVertical: 16,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 6,
    elevation: 3,
  },
  summaryTitle: { fontWeight: '600', fontSize: 16 },
  statValue: { fontSize: 18, fontWeight: '600', textAlign: 'center' },
  statLabel: { fontSize: 12, color: '#7F8C8D', textAlign: 'center' },
  summaryDate: {
    position: 'absolute', right: 16, top: 16, fontSize: 12, color: '#7F8C8D'
  },

  sectionHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginVertical: 8 },
  sectionTitle: { fontSize: 18, fontWeight: '600' },

  orderCard: { backgroundColor: '#fff', borderRadius: 12, padding: 16, paddingTop: 32, marginVertical: 8, position: 'relative' },
  badge: { position: 'absolute', top: 8, left: 16, paddingHorizontal: 8, paddingVertical: 4, borderRadius: 8 },
  urgent: { backgroundColor: '#FDEDEC' },
  regular: { backgroundColor: '#FEF9E7' },
  badgeText: { fontSize: 12, fontWeight: '600', color: '#C0392B' },

  orderRow: { flexDirection: 'row', justifyContent: 'space-between', marginBottom: 4 },
  orderNumber: { fontWeight: '600' },
  timer: { color: '#FF0000' },

  customer: { color: '#2C3E50', marginBottom: 8 },

  row: { flexDirection: 'row', alignItems: 'center', marginVertical: 4 },
  medsLabel: { marginLeft: 4, fontWeight: '600' },
  medsText: { marginLeft: 20, fontSize: 14 },

  infoText: { marginLeft: 4, fontSize: 14 },

  buttonRow: { flexDirection: 'row', justifyContent: 'space-between', marginTop: 12 },
  bidButton: { flex: 1, backgroundColor: '#000', padding: 12, borderRadius: 8, marginRight: 8, alignItems: 'center' },
  bidText: { color: '#fff', fontWeight: '600' },
  rejectButton: { flex: 1, borderColor: '#000', borderWidth: 1, padding: 12, borderRadius: 8, alignItems: 'center' },
  rejectText: { color: '#000', fontWeight: '600' },

  offlineContainer: { alignItems: 'center', justifyContent: 'center', marginTop: 40 },
  offlineImage: { width: 150, height: 150, marginBottom: 16 },
  offlineText: { fontSize: 18, color: '#888', marginBottom: 16 },
});
