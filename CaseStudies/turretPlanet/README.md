      IF windPower IS min THEN considerWindPowerF(1);
      IF windPower IS mid THEN considerWindPowerF(4.1);
      IF windPower IS max THEN considerWindPowerF(8.2);

      IF deltaX IS min THEN degAndVelX(-0.5);
      IF deltaX IS mid THEN degAndVelX(-0.7);
      IF deltaX IS max THEN degAndVelX(-0.9);

      IF deltaXneg IS min THEN degAndVelX(0.5);
      IF deltaXneg IS mid THEN degAndVelX(0.7);
      IF deltaXneg IS max THEN degAndVelX(1.0);

      IF deltaY IS min THEN degAndVelY(-4.0);
      IF deltaY IS mid THEN degAndVelY(-5.0);
      IF deltaY IS max THEN degAndVelY(-5.5);

      IF deltaYneg IS min THEN degAndVelY(2.5);
      IF deltaYneg IS mid THEN degAndVelY(3.5);
      IF deltaYneg IS max THEN degAndVelY(4.5);
