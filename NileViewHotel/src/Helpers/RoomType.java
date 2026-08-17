package Helpers;
public enum RoomKind {
    STANDARD {
        @Override
        public boolean isCancellable() {
            return true;
        }
    },
    DELUXE {
        @Override
        public boolean isCancellable() {
            return true;
        }
    },
    SUITE {
        @Override
        public boolean isCancellable() {
            return false;
        }
    },
    FAMILY {
        @Override
        public boolean isCancellable() {
            return false;
        }
    };

    public abstract boolean isCancellable();
}
