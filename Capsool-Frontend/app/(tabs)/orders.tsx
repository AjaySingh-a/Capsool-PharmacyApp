'use client';

import {
  View,
  Text,
  TextInput,
  ScrollView,
  TouchableOpacity,
  Image,
  Switch,
  StyleSheet,
} from 'react-native';
import { Search, Filter, MapPin, Phone, Lock } from 'lucide-react-native';
import { useState } from 'react';
import { Ionicons } from '@expo/vector-icons';

export default function Orders() {
  const [activeTab, setActiveTab] = useState<'Ongoing' | 'Delivered'>('Ongoing');
  const [isOnline, setIsOnline] = useState(true);

  return (
    <ScrollView
      style={styles.container}
      contentContainerStyle={{ paddingBottom: 24 }}
    >
      {/* Header */}
      <View style={styles.header}>
        <Ionicons name="person-circle-outline" size={28} color="#000" />
        <Image
          source={require('@/assets/images/logo.png')}
          style={styles.logo}
          resizeMode="contain"
        />
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

      {/* Spacer between header & search */}
      <View style={{ height: 16 }} />

      {/* Search and Filter */}
      <View style={styles.searchRow}>
        <View style={styles.searchBox}>
          <Search size={20} color="black" />
          <TextInput
            placeholder="Search Orders"
            placeholderTextColor="gray"
            style={styles.searchInput}
          />
        </View>
        <TouchableOpacity style={styles.filterButton}>
          <Filter size={20} color="black" />
        </TouchableOpacity>
      </View>

      {/* Spacer between search & tabs */}
      <View style={{ height: 24 }} />

      {/* Tabs */}
      <View style={styles.tabsRow}>
        <TouchableOpacity onPress={() => setActiveTab('Ongoing')}>
          <Text
            style={[
              styles.tabText,
              activeTab === 'Ongoing' && styles.tabTextActive,
            ]}
          >
            Ongoing
          </Text>
          {activeTab === 'Ongoing' && <View style={styles.tabUnderline} />}
        </TouchableOpacity>

        <TouchableOpacity onPress={() => setActiveTab('Delivered')}>
          <Text
            style={[
              styles.tabText,
              activeTab === 'Delivered' && styles.tabTextActive,
            ]}
          >
            Delivered
          </Text>
          {activeTab === 'Delivered' && <View style={styles.tabUnderline} />}
        </TouchableOpacity>
      </View>

      {/* Recent Orders Title */}
      <View style={styles.recentRow}>
        <Text style={styles.recentTitle}>Recent Orders</Text>
        <TouchableOpacity style={styles.sortRow}>
          <Text style={styles.sortText}>Sort by date</Text>
          <Filter size={16} color="#888888" style={{ marginLeft: 4 }} />
        </TouchableOpacity>
      </View>

      {/* Order Card */}
      <View style={styles.card}>
        <View style={styles.cardHeader}>
          <View style={styles.badgeUrgent}>
            <Text style={styles.badgeText}>Urgent</Text>
          </View>
          <Text style={styles.timeText}>2 mins ago</Text>
        </View>

        <Text style={styles.orderNumber}>Order #8294</Text>
        <Text style={styles.customerName}>Sarah Thompson</Text>

        <View style={styles.statusBadge}>
          <Text style={styles.statusText}>Out for Delivery</Text>
        </View>

        <View style={styles.infoRow}>
          <Lock size={18} color="black" />
          <View style={{ marginLeft: 8 }}>
            <Text style={styles.infoLabel}>Medications:</Text>
            <Text style={styles.infoValue}>- Paracetamol 650mg (x10)</Text>
            <Text style={styles.infoValue}>- Amoxicillin 500mg (x20)</Text>
          </View>
        </View>

        <View style={styles.infoRow}>
          <MapPin size={18} color="black" />
          <Text style={styles.infoValue}>
            742 Evergreen Terrace, Springfield
          </Text>
        </View>

        <View style={styles.infoRow}>
          <Phone size={18} color="black" />
          <Text style={styles.infoValue}>7574258543</Text>
        </View>

        <TouchableOpacity style={styles.trackButton}>
          <Text style={styles.trackText}>Track Order</Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#F5F7F8', paddingHorizontal: 16, paddingTop: 24 },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  logo: { width: 120, height: 32 },
  toggleRow: { flexDirection: 'row', alignItems: 'center' },
  onlineText: { marginRight: 8, fontWeight: '600', color: '#000' },

  searchRow: { flexDirection: 'row', alignItems: 'center' },
  searchBox: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
    borderRadius: 24,
    paddingHorizontal: 16,
    paddingVertical: 12,
    marginRight: 12,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  searchInput: { marginLeft: 8, flex: 1, fontSize: 16, color: 'black' },
  filterButton: {
    backgroundColor: '#FFFFFF',
    borderRadius: 24,
    padding: 12,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },

  tabsRow: {
    flexDirection: 'row',
    justifyContent: 'space-around',
  },
  tabText: { fontSize: 16, fontWeight: '600', color: '#888888' },
  tabTextActive: { color: '#000000' },
  tabUnderline: {
    height: 2,
    backgroundColor: '#000000',
    marginTop: 4,
    borderRadius: 1,
  },

  recentRow: { flexDirection: 'row', justifyContent: 'space-between', marginBottom: 16 },
  recentTitle: { fontSize: 18, fontWeight: '700' },
  sortRow: { flexDirection: 'row', alignItems: 'center' },
  sortText: { fontSize: 14, color: '#888888' },

  card: {
    backgroundColor: '#FFFFFF',
    borderRadius: 24,
    padding: 16,
    marginBottom: 24,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  cardHeader: { flexDirection: 'row', justifyContent: 'space-between', marginBottom: 12 },
  badgeUrgent: {
    backgroundColor: '#FFEAEA',
    paddingHorizontal: 12,
    paddingVertical: 4,
    borderRadius: 12,
  },
  badgeText: { color: '#FF3B30', fontWeight: '600', fontSize: 12 },
  timeText: { color: '#888888', fontSize: 12 },

  orderNumber: { fontSize: 18, fontWeight: '700', marginBottom: 4 },
  customerName: { fontSize: 14, color: '#666666', marginBottom: 12 },

  statusBadge: {
    alignSelf: 'flex-start',
    backgroundColor: '#E0ECF8',
    paddingHorizontal: 12,
    paddingVertical: 4,
    borderRadius: 12,
    marginBottom: 12,
  },
  statusText: { color: '#007AFF', fontWeight: '600', fontSize: 12 },

  infoRow: { flexDirection: 'row', alignItems: 'flex-start', marginBottom: 12 },
  infoLabel: { fontSize: 14, fontWeight: '600', marginBottom: 4 },
  infoValue: { fontSize: 14, color: '#666666', marginLeft: 8 },

  trackButton: {
    backgroundColor: '#000000',
    borderRadius: 24,
    paddingVertical: 12,
    alignItems: 'center',
  },
  trackText: { color: '#FFFFFF', fontWeight: '700', fontSize: 16 },
});
