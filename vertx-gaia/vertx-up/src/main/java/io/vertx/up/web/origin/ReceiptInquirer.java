package io.vertx.up.web.origin;

import io.vertx.up.atom.worker.Receipt;
import io.vertx.up.log.Annal;
import io.vertx.up.web.thread.QueueThread;
import io.zero.epic.fn.Fn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Receipt
 */
public class ReceiptInquirer implements Inquirer<Set<Receipt>> {

    private static final Annal LOGGER = Annal.get(ReceiptInquirer.class);

    @Override
    public Set<Receipt> scan(final Set<Class<?>> queues) {
        final List<QueueThread> threadReference = new ArrayList<>();
        /** 3.1. Build Metadata **/
        for (final Class<?> queue : queues) {
            final QueueThread thread =
                    new QueueThread(queue);
            threadReference.add(thread);
            thread.start();
        }
        /** 3.2. Join **/
        Fn.safeJvm(() -> {
            for (final QueueThread item : threadReference) {
                item.join();
            }
        }, LOGGER);
        /** 3.3. Return **/
        final Set<Receipt> receipts = new HashSet<>();
        Fn.safeJvm(() -> {
            for (final QueueThread item : threadReference) {
                receipts.addAll(item.getReceipts());
            }
        }, LOGGER);
        return receipts;
    }
}
