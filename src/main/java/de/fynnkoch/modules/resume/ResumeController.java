package de.fynnkoch.modules.resume;

import static de.fynnkoch.core.helpers.EntityHelper.checkIfUnmodifiedSince;
import static lombok.AccessLevel.PRIVATE;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ResumeController implements ResumeContract {

  ResumeService resumeService;
  ResumeMapper resumeMapper;

  @Override
  public List<ResumeView> getAll() {
    return this.resumeService.getAll().stream().map(this.resumeMapper::toView).toList();
  }

  @Override
  public ResumeView getOne(@NonNull final UUID resumeId) {
    return this.resumeMapper.toView(this.resumeService.getOne(resumeId));
  }

  @Override
  public ResumeView create(@NonNull final ResumeCreateOrder resumeCreateOrder) {
    final var resume = this.resumeMapper.fromCreateOrder(resumeCreateOrder);
    return this.resumeMapper.toView(this.resumeService.save(resume));
  }

  @Override
  public ResumeView update(
      @NonNull final UUID resumeId,
      @NonNull final ResumeUpdateOrder resumeUpdateOrder,
      @NonNull final ZonedDateTime ifModifiedSince) {
    final var existingResume = this.resumeService.getOne(resumeId);

    checkIfUnmodifiedSince(resumeId, ifModifiedSince, existingResume);

    final var resume = this.resumeMapper.fromUpdateOrder(existingResume, resumeUpdateOrder);
    return this.resumeMapper.toView(this.resumeService.save(resume));
  }

  @Override
  public ResumeView toggleStatus(
      @NonNull final UUID resumeId, @NonNull final ZonedDateTime ifModifiedSince) {
    final var existingResume = this.resumeService.getOne(resumeId);

    checkIfUnmodifiedSince(resumeId, ifModifiedSince, existingResume);

    return this.resumeMapper.toView(this.resumeService.toggleStatus(existingResume));
  }

  @Override
  public void delete(@NonNull final UUID resumeId, @NonNull final ZonedDateTime ifModifiedSince) {
    final var existingResume = this.resumeService.getOne(resumeId);

    checkIfUnmodifiedSince(resumeId, ifModifiedSince, existingResume);

    this.resumeService.delete(existingResume);
  }
}
